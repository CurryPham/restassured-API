package test;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import model.RequestCapability;
import utils.AuthenticationHandle;

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
        String fieldsStr = "{\n" +
                "  \"fields\": {\n" +
                "    \"summary\": \"Summary | From Jira API\",\n" +
                "    \"project\": {\n" +
                "      \"key\": \"RA\"\n" +
                "    },\n" +
                "    \"issuetype\": {\n" +
                "      \"id\": \"10001\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        // Send request
        Response response = request.body(fieldsStr).post(path);
        response.prettyPrint();

    }


}
