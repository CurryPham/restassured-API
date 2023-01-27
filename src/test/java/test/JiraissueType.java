package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.RequestCapability;
import org.apache.commons.codec.binary.Base64;

import static io.restassured.RestAssured.given;

public class JiraissueType implements RequestCapability {

    public static void main(String[] args) {
        String baseUri = "https://restassuredapi.atlassian.net";
        String pathUrl = "/rest/api/3/project/RA";

        String email = "khoapd2000@gmail.com";
        String apiToken = "r4PV3w5mfeQ26Q4xhSfvF2AF";
        String cred = email.concat(":").concat(apiToken);
        byte[] encodedCred = Base64.encodeBase64(cred.getBytes());
        String encodedCredStr = new String(encodedCred);

        RequestSpecification request = given();
        request.baseUri(baseUri);
        request.header(defaultHeader);
        request.header(getAuthenticatedHeader.apply(encodedCredStr));

        Response response = request.get(pathUrl);
        response.prettyPrint();

    }
}
