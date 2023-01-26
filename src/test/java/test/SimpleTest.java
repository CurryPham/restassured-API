package test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class SimpleTest {



    public static void main(String[] args) throws Exception {
        String basseUrl = "https://jsonplaceholder.typicode.com";

        // Request scope
        RequestSpecification requestSpec = given();
        requestSpec.baseUri(basseUrl);
        requestSpec.basePath("/todos");

        // Response scope
        Response response = requestSpec.get("/1");
        response.prettyPeek();
        response.then().body("userId", equalTo(1));
        response.then().body("id", equalTo(1));
        response.then().body("title", equalTo("delectus aut autem"));
        response.then().body("completed", equalTo(false));


    }
}
