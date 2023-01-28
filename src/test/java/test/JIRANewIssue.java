package test;


import builder.BodyJSONBuilder;
import builder.IsssueContentBuilder;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import model.IssueFields;
import model.RequestCapability;
import org.apache.commons.lang3.RandomStringUtils;
import utils.AuthenticationHandler;
import utils.ProjectInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.restassured.RestAssured.given;


public class JIRANewIssue implements RequestCapability {

    public static void main(String[] args) {
        // API Info
        String baseUri = "https://restassuredapi.atlassian.net";
        String projectKey = "RA";
        String path = "/rest/api/3/issue";

        String email = "khoapd2000@gmail.com";
        String apiToken = "JzYO2lUGv92ZAfeckS7t8A56";
        String encodedCredStr = AuthenticationHandler.getEncodedCredStr(email, apiToken);

        // Request object
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJSONHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));

        // Define body data
        ProjectInfo projectInfo = new ProjectInfo(baseUri, projectKey);
        String taskTypeId = projectInfo.getIssueTypeId("task");
        int desiredLength = 20;
        boolean hasLetters = true;
        boolean hasNumbers = true;
        String randomSummary = RandomStringUtils.random(desiredLength, hasLetters, hasNumbers);
        IsssueContentBuilder isssueContentBuilder = new IsssueContentBuilder();
        String issueFieldContent = isssueContentBuilder.build(projectKey, taskTypeId, randomSummary);
//        IssueFields issueFields = isssueContentBuilder.getIssueFields();
//        System.out.println(issueFields.getFields().getSummary());
//        System.out.println(issueFields.getFields().getProject().getKey());
//        System.out.println(issueFields.getFields().getIssuetype().getId());

        // CREATE JIRA TASK
        Response response = request.body(issueFieldContent).post(path);
        response.prettyPrint();

        // Check created task details
        Map<String,String> responseBody = JsonPath.from(response.asString()).get();
        final String CREATED_ISSUE_KEY = responseBody.get("key");
        IssueFields issueFields = isssueContentBuilder.getIssueFields();
        String exptectedSummary = issueFields.getFields().getSummary();
        String exptectedStatus = "To do";


        Function<String, Map<String, String>> getIssueInfo = issueKey -> {
            String getIssuePath = "/rest/api/3/issue/" + issueKey;
            // READ CREATEED JIRA TASK INFO
            Response response_ = request.get(getIssuePath);

            Map<String, Object> fields = JsonPath.from(response_.getBody().asString()).get("fields");
            String actualSummary = fields.get("summary").toString();
            Map<String, Object> status = (Map<String, Object>) fields.get("status");
            Map<String, Object> statusCategory = (Map<String, Object>) status.get("statusCategory");
            String actualStatus = statusCategory.get("name").toString();

            Map<String, String> issueInfo = new HashMap<>();
            issueInfo.put("summary", actualSummary);
            issueInfo.put("status", actualStatus);
            return issueInfo;
        };


        Map<String, String> issueInfo = getIssueInfo.apply(CREATED_ISSUE_KEY);


        System.out.println("expectedSummary: "  + exptectedSummary);
        System.out.println("actualStatus: " + issueInfo.get("summary"));

        System.out.println("expectedSummary: " + exptectedStatus);
        System.out.println("expectedStatus: " + issueInfo.get("status"));


        // UPDATE CREATED JIRA TASK
        String isssueTransitionPath = "/rest/api/3/issue/" + CREATED_ISSUE_KEY + "/transitions";
        String transitionBody = "{\n" +
                "  \"transition\" : {\n" +
                "    \"id\" : \"31\"\n" +
                "  }\n" +
                "}";
        request.body(transitionBody).post(isssueTransitionPath).then().statusCode(204);
        issueInfo = getIssueInfo.apply(CREATED_ISSUE_KEY);
        String latestIssueStatus = issueInfo.get("status");
        System.out.println("lastestIssuesStatus: " + latestIssueStatus);
    }
}
