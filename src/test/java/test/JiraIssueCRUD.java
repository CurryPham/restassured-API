package test;


import api_flow.IssueFlow;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.testng.annotations.Test;
import utils.AuthenticationHandler;

import static io.restassured.RestAssured.given;


public class JiraIssueCRUD implements RequestCapability {

    @Test
    public static void testE2EFlow() {
        // API Info
        String baseUri = "https://restassuredapi.atlassian.net";
        String projectKey = "RA";
        String path = "/rest/api/3/issue";

        String email = "khoapd2000@gmail.com";
        String apiToken = "ic3Nhmlwv0kuSydH6FRy9916";
        String encodedCredStr = AuthenticationHandler.getEncodedCredStr(email, apiToken);

        // Request object
        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(acceptJSONHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));


        IssueFlow issueFlow = new IssueFlow(request, baseUri, projectKey, "task");
        System.out.println("->> CREATE");
        issueFlow.createIssue();

        System.out.println("->> READ");
        issueFlow.verifyIssueDetails();

        System.out.println("->> UPDATE");
        issueFlow.updateIssue("Done");
        issueFlow.verifyIssueDetails();

        System.out.println("->> DELETE");
        issueFlow.deleteIssue();
    }
}
