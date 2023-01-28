package test;


import builder.BodyJSONBuilder;
import builder.IsssueContentBuilder;
import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import model.IssueFields;
import model.RequestCapability;
import utils.AuthenticationHandle;
import utils.ProjectInfo;

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
        String summary = "This is my summary";
        String issueFieldContent = IsssueContentBuilder.build(projectKey, taskTypeId, summary);

        // Send request
        Response response = request.body(issueFieldContent).post(path);
        response.prettyPrint();

    }
}
