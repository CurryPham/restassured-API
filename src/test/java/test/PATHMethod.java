package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.BuildModleJSON;
import model.PostBody;
import model.RequestCapability;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PATHMethod implements RequestCapability {



    public static void main(String[] args){
        String basseUrl = "https://jsonplaceholder.typicode.com";

        // Form up request  instance, headers and baseUrl
        RequestSpecification requestSpec = given();
        requestSpec.baseUri(basseUrl);
        requestSpec.header(defaultHeader);

        // Form up body
        PostBody postBody = new PostBody();
        postBody.setTitle("New title");
        String postBodyStr = BuildModleJSON.parseJsonString(postBody);
        final String TARGET_POST_ID = "1";
        Response response = requestSpec.body(postBodyStr).patch("/posts/".concat(TARGET_POST_ID));
        response.then().body("title", equalTo(postBody.getTitle()));
        response.prettyPrint();
    }
}
