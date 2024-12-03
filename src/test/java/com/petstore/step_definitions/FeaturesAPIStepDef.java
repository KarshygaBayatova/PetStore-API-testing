package com.petstore.step_definitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.pojos.Order;
import com.petstore.utilities.APIUtils;
import com.petstore.utilities.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class FeaturesAPIStepDef {

    RequestSpecification givenPart;
    Response response;
    ValidatableResponse thenPart;

    // Verify that the user can create user with "/user" endpoint
    @Given("Accept header is {string}")
    public void accept_header_is(String acceptHeader) {
        givenPart = RestAssured.given()
                        .accept(acceptHeader);
    }
    @Given("Content type header is {string}")
    public void content_type_header_is(String contentType) {
        givenPart.contentType(contentType);
    }
    @And("Request body is {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void requestBody(String id, String username, String firstName, String lastName, String email, String password, String phone, String userStatus) {
        Map<String, Object> requestBody = new LinkedHashMap<>();
        requestBody.put("id", id);
        requestBody.put("username", username);
        requestBody.put("firstName", firstName);
        requestBody.put("lastName", lastName);
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("phone", phone);
        requestBody.put("userStatus", userStatus);

        givenPart.body(requestBody);
    }
    @When("User send POST request {string} endpoint")
    public void user_send_post_request_endpoint(String endPoint) {
        response = givenPart.when().post(ConfigurationReader.getProperty("baseUri") + endPoint);

    }
    @Then("Status code should be {int}")
    public void status_code_should_be(Integer statusCode) {
        thenPart = response.then();
        thenPart.statusCode(statusCode);
    }
    @Then("Response Content type is {string}")
    public void response_content_type_is(String responseContentType) {
        thenPart.contentType(responseContentType);
    }
    @Then("code in the response body is {int}")
    public void code_in_the_response_body_is(Integer codeValue) {
        thenPart.body("code", equalTo(codeValue));
    }

    //Verify that the newly created user can login to the system

    @And("Path parameter is key {string} and value is {string}")
    public void pathParameterIsKeyAndValueIs(String key, String value) {
        givenPart.queryParam(key, value);
    }

    @When("User send GET request {string} endpoint")
    public void userSendGETRequestEndpoint(String endpoint) {
        response = givenPart.when().get(ConfigurationReader.getProperty("baseUri") + endpoint);
    }

    @And("message in the response body contains {string}")
    public void messageInTheResponseBodyContains(String message) {
        thenPart.body("message", containsString(message));
    }

    // Verify that the user can get user info by username "/user/{username}" endpoint
    @And("Path parameter is {string} and value is {string}")
    public void pathParameterIsAndValueIs(String key, String value) {
        givenPart.pathParam(key, value);
    }

    @When("User send GET request {string}")
    public void userSendGETRequest(String endpoint) {
        response = givenPart.when().get(ConfigurationReader.getProperty("baseUri") + endpoint);
    }

    @And("User first name should be {string}")
    public void userFirstNameShouldBe(String firstName) {
        thenPart.body("firstName", equalTo(firstName));
    }

    @And("User last name should be {string}")
    public void userLastNameShouldBe(String lastName) {
        thenPart.body("lastName", is(lastName));
    }

    @And("User email should be {string}")
    public void userEmailShouldBe(String email) {
        thenPart.body("email", equalTo(email));
    }

    @And("User password should be {string}")
    public void userPasswordShouldBe(String password) {
        thenPart.body("password", equalTo(password));
    }

    @And("User phone should be {string}")
    public void userPhoneShouldBe(String phone) {
        thenPart.body("phone", is(phone));
    }

    @And("User status should be {int}")
    public void userStatusShouldBe(int userStatus) {
        thenPart.body("userStatus", equalTo(userStatus));
    }

    // Verify that the user can update the existing user
    @When("User send PUT request {string} endpoint")
    public void userSendPUTRequestEndpoint(String endpoint) {
        response = givenPart.when().put(ConfigurationReader.getProperty("baseUri")+ endpoint);
    }

    // Verify that the user can delete the user by username
    @When("User send DELETE request {string}")
    public void userSendDELETERequest(String endpoint) {
        response = givenPart.when().delete(ConfigurationReader.getProperty("baseUri") + endpoint);
    }

    @And("message in the response body is {string}")
    public void messageInTheResponseBodyIs(String message) {
        thenPart.body("message", equalTo(message));
    }

    @And("The following fields in the response body is not null")
    public void theFollowingFieldsInTheResponseBodyIsNotNull(List<String> expectedResponseFields) {
        for(String each : expectedResponseFields) {
            thenPart.body(each, is(notNullValue()));
        }
    }

    // Verify that the User can post new order
    Order expectedOrderInfo;
    ObjectMapper objectMapper = new ObjectMapper();
    @And("User have request body")
    public void userHaveRequestBody() throws IOException {

        String requestBody = new String(Files.readAllBytes(Paths.get("src/test/resources/features/order.json")));
        givenPart.body(requestBody);
    }

    @And("The response body should contain the order details")
    public void theResponseBodyShouldContainTheOrderDetails() {

        // retrieving data from the API response body
        Map<String, Object> actualOrderData = response.jsonPath().getMap("");
        //System.out.println(actualOrderData);

        // retrieving data from the json file
        String filePath = "src/test/resources/features/order.json";
        Map<String, Object> expectedOrderData = APIUtils.readOrderFromJson(filePath);

        Assertions.assertEquals(actualOrderData, expectedOrderData);
    }
}
