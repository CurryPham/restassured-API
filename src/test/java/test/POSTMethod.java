package test;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.PostBody;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.equalTo;

public class POSTMethod {



    public static void main(String[] args) throws Exception {
        String basseUrl = "https://jsonplaceholder.typicode.com";

        // Request scope
        RequestSpecification requestSpec = given();
        requestSpec.baseUri(basseUrl);


        // Conten-type -> Header
        requestSpec.header(new Header("Content-Type", "application/json; charset=UTF-8"));

        // Form up request body
//       String postBody = "{\n" +
//               "\"userId\": 1,\n" +
//               "\"id\": 1,\n" +
//               "\"title\": \"The req'tittle\",\n" +
//               "\"body\": \"The req' body\"\n" +
//               "}";

       // Gson
        Gson gson = new Gson();
        PostBody postBody = new PostBody();
        postBody.setUserId(1);
        postBody.setId(101);
        postBody.setTitle("The req'tittle");
        postBody.setBody("The req' body");

        // Send POST request
        Response response = requestSpec.body(gson.toJson(postBody)).post("/posts");
        response.prettyPrint();

        // Verification
        requestSpec.then().statusCode(equalTo(201));
        requestSpec.then().statusLine(containsStringIgnoringCase("201 Created"));
        requestSpec.then().body("userId", equalTo(1));
        requestSpec.then().body("title", equalTo("The req'tittle"));
        requestSpec.then().body("body", equalTo("The req' body"));
    }
}
