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
import utils.AuthenticationHandle;
import utils.ProjectInfo;

import java.util.Map;

import static io.restassured.RestAssured.given;


public class JIRANewIssue implements RequestCapability {

    public static void main(String[] args) {
        // API Info
        String baseUri = "https://restassuredapi.atlassian.net";
        String projectKey = "RA";
        String path = "/rest/api/3/issue";

        String email = "khoapd2000@gmail.com";
        String apiToken = "VDT9pQreIsn5B8XZyoiS7193";
        String encodedCredStr = AuthenticationHandle.getEncodedCredStr(email, apiToken);

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
        String getIssuePath = "/rest/api/3/issuetype/" + responseBody.get("key");

        // READ CREATED JIRA TASK INFO
        response = request.get(getIssuePath);


        IssueFields issueFields = isssueContentBuilder.getIssueFields();
        String exptectedSummary = issueFields.getFields().getSummary();
        String exptectedStatus = "To do";

        Map<String, Object> fields = JsonPath.from(response.getBody().asString()).get("fields");
        String actualSummary = fields.get("summary").toString();
        Map<String, Object> status = (Map<String, Object>) fields.get("status");
        Map<String, Object> statusCategory = (Map<String, Object>) status.get("statusCategory");
        String actualStatus = statusCategory.get("name").toString();

        System.out.println("expectedSummary" + exptectedSummary);
        System.out.println("actualStatus" + actualSummary);

        System.out.println("expectedSummary" + exptectedStatus);
        System.out.println("expectedStatus" + actualStatus);

    }
}
