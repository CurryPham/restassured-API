package test;

import com.google.gson.Gson;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.PostBody;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class PUTMethod {

    public static void main(String[] args) {
        String basseUrl = "https://jsonplaceholder.typicode.com";

        // Form up request and heaader
        RequestSpecification requestSpec = given();
        requestSpec.baseUri(basseUrl);
        requestSpec.header(new Header("Content-Type", "application/json; charset=UTF-8"));

        // Construct body
        PostBody postBody1 = new PostBody(1, 1, "New title 1", "New body 1");
        PostBody postBody2 = new PostBody(1, 1, "New title 2", "New body 2");
        PostBody postBody3 = new PostBody(1, 1, "New title 3", "New body 3");

        List<PostBody> postBodyList = Arrays.asList(postBody1, postBody2, postBody3);
        for (PostBody postBody : postBodyList){
            System.out.println(postBody);
            Gson gson = new Gson();
            String postBodyStr = gson.toJson(postBody);

        // Send request
        final int TARGET_POST_NUM = 1;
        Response response =  requestSpec.body(postBodyStr).put("/posts/".concat(String.valueOf(TARGET_POST_NUM)));
        response.then().body("userId", equalTo(postBody.getUserId()));
        response.then().body("id", equalTo(postBody.getId()));
        response.then().body("title", equalTo(postBody.getTitle()));
        response.then().body("body", equalTo(postBody.getBody()));
        }
    }
}
