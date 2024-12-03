package com.petstore.step_definitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.utilities.APIUtils;
import com.petstore.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class APIStepDef {

    RequestSpecification reqSpec = given().accept(ContentType.JSON);

    @Order(1)
    @DisplayName("POST /user/createWithList")
    @Test
    public void createUsersWithList() throws Exception{

        String requestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/features/users.json")));

        reqSpec.contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(ConfigurationReader.getProperty("baseUri") + "/user/createWithList")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("code", equalTo(200))
                .body("message", is("ok"));
    }

    @Order(2)
    @DisplayName("GET /user/login")
    @ParameterizedTest
    @CsvFileSource(resources = "/features/userCredentials.csv", numLinesToSkip = 1)
    public void login(String username, String password){
        reqSpec.queryParam(username, password)
                .when().get(ConfigurationReader.getProperty("baseUri") + "/user/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("code", equalTo(200))
                .body("message", containsString("logged in user session:"));
    }

    @Order(3)
    @DisplayName(("GET /user/{username}"))
    @ParameterizedTest
    @ValueSource(strings = {"user01", "user02", "user03", "user04", "user05"})
    public void test1(String username){

        Response response = reqSpec
                .pathParam("username", username)
                .when().get(ConfigurationReader.getProperty("baseUri") + "/user/{username}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        // retrieving data from the API response body
        Map<String, Object> actualUserData = response.jsonPath().getMap("");
        //System.out.println(actualUserData);

        // retrieving data from the json file
        String filePath = "src/test/resources/features/users.json";
        Map<String, Map<String, Object>> allUsersData = APIUtils.readUserFromJson(filePath);

        Map<String, Object> expectedUserData = allUsersData.get(username);
       // System.out.println("Expected user data: " + expectedUserData);

        Assertions.assertEquals(actualUserData, expectedUserData, "Mismatch for user: " + username);
    }

    private static Map<String, Object> updatedUserInfo;
    @Order(4)
    @DisplayName("PUT /user/{username}")
    @Test
    public void test2(){
        /*
        "id": 2,
                "username": "user02",
                "firstName": "John",
                "lastName": "Kim",
                "email": "john@example.com", ===> john@gmail.com
                "password": "password2",
                "phone": "0987654321",
                "userStatus": 1   ====> 0
 */
        updatedUserInfo = new LinkedHashMap<>();
        updatedUserInfo.put("id", 2);
        updatedUserInfo.put("username", "user02");
        updatedUserInfo.put("firstName", "John");
        updatedUserInfo.put("lastName", "Kim");
        updatedUserInfo.put("email", "john@gmail.com");
        updatedUserInfo.put("password", "password2");
        updatedUserInfo.put("phone", "000000000");
        updatedUserInfo.put("userStatus", 0);

        reqSpec.contentType(ContentType.JSON)
                .pathParam("username", "user02")
                .body(updatedUserInfo)
                .when().put(ConfigurationReader.getProperty("baseUri") + "/user/{username}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("code", equalTo(200))
                .body("message", equalTo("2"));
    }

    @Order(5)
    @DisplayName("GET updated user info /user/{username}")
    @Test
    public void test3(){

        Map<String, Object> expectedData = updatedUserInfo;

        Response response = reqSpec
                .contentType(ContentType.JSON)
                .pathParam("username", "user02")
                .when().get(ConfigurationReader.getProperty("baseUri") + "/user/{username}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response();

        Map<String, Object> actualData = response.jsonPath().getMap("");
        System.out.println("Printing actual data: " + actualData);

        Assertions.assertEquals(expectedData, actualData);
    }

    @Order(6)
    @DisplayName("Delete /user/{username}")
    @Test
    public void deleteUser(){

        JsonPath jp = reqSpec
                .pathParam("username", "user05")
                .when().delete(ConfigurationReader.getProperty("baseUri") + "/user/{username}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().response().jsonPath();

        Assertions.assertEquals(200, jp.getInt("code"));
        Assertions.assertEquals("user05", jp.getString("message"));
    }

    @Order(7)
    @DisplayName("Get /user/{username}")
    @Test
    public void verifyUserDeleted(){

        reqSpec.pathParam("username", "user05")
                .when().get(ConfigurationReader.getProperty("baseUri") + "/user/{username}")
                .then()
                .statusCode(404)
                .contentType(ContentType.JSON);
    }

    @Order(8)
    @DisplayName("GET /user/logout")
    @Test
    public void verifyUserLogout(){

        reqSpec.when().get(ConfigurationReader.getProperty("baseUri") + "/user/logout")
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("message", equalTo("ok"));
    }

}
