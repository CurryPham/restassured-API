package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProjectInfo implements RequestCapability {

    private String baseUri;
    private String projectKey;
    private List<Map<String,String>> issueTypes;
    private Map<String, List<Map<String, String>>> projectInfo;

    public ProjectInfo(String baseUri, String projectKey) {

        this.baseUri = baseUri;
        this.projectKey = projectKey;
        getProjectInfo();
    }

    private void getIssueTypes(){
        issueTypes = projectInfo.get("issueTypes");
    }

    public String getIssueTypeId(String issueTypeStr) {
        getIssueTypes();

        String issueTypeId = null;

        for (Map<String, String> issueType : issueTypes) {
            if (issueType.get("name").equalsIgnoreCase(issueTypeStr)) {
                issueTypeId = issueType.get("id");
                break;
            }
        }

        if ( issueTypeId == null ) {
            throw new IllegalArgumentException("[ERR] Could not find the is for " + issueTypeStr);
        }
        return issueTypeId;
    }

    private void getProjectInfo(){
        String pathUrl = "/rest/api/3/project/".concat(projectKey);

        String email = "khoapd2000@gmail.com";
        String apiToken = "pGNJx9h7sT2F5it0WNGRBC4C";
        String encodedCredStr = AuthenticationHandler.getEncodedCredStr(email, apiToken);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));
        Response response = request.get(pathUrl);
        projectInfo = JsonPath.from(response.asString()).get();
    }
}

