package com.sdet.stepdefs;

import com.sdet.utils.BaseConfig;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserStepDefs {

    private Response response;

    @Before
    public void setup() {
        BaseConfig.setup();
    }

    @Given("the API is available")
    public void theApiIsAvailable() {
        // Base config is set up in @Before
        System.out.println("✅ API Base Config ready: https://reqres.in");
    }

    @When("I request the list of users on page {int}")
    public void iRequestListOfUsers(int page) {
        response = given()
                //.queryParam("page", page)
                .when()
                .get("/users")
                .then()
                .extract()
                .response();

        System.out.println("✅ Response received. Status: " + response.getStatusCode());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatus) {
        assertEquals(response.getStatusCode(), expectedStatus,
                "Status code mismatch!");
        System.out.println("✅ Status code verified: " + expectedStatus);
    }

    @Then("the response should contain a list of users")
    public void theResponseShouldContainUsers() {
        int userCount = response.jsonPath().getList("data").size();
        assertTrue(userCount > 0, "User list is empty!");
        System.out.println("✅ Users found in response: " + userCount);
    }

    @Then("each user should have an id, email and first name")
    public void eachUserShouldHaveFields() {
        List<Map<String, ?>> users = response.jsonPath().getList("$");
        assertTrue(users.size() > 0, "User list is empty!");
        for (Map<String, ?> user : users) {
            assertNotNull(user.get("id"), "id is null");
            assertNotNull(user.get("email"), "email is null");
            assertNotNull(user.get("username"), "username is null");
        }
        System.out.println("✅ All " + users.size() + " users have id, email and username");
    }
    
    @When("I create a user with name {string} and job {string}")
    public void iCreateAUser(String name, String job) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("job", job);

        response = given()
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();
        
        
        System.out.println("✅ Create user response: " + response.getBody().asString());
    }

    @Then("the response should contain the created user details")
    public void theResponseShouldContainCreatedUser() {
        assertNotNull(response.jsonPath().get("id"), "id is null");
        System.out.println("✅ Created user id: " + response.jsonPath().get("id"));
        System.out.println("✅ Full response: " + response.getBody().asString());
    }
    @When("I update user {int} with name {string} and job {string}")
    public void iUpdateAUser(int userId, String name, String job) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("job", job);

        response = given()
                .body(requestBody)
                .when()
                .put("/users/" + userId)
                .then()
                .extract()
                .response();

        System.out.println("✅ Update user response: " + response.getBody().asString());
    }

    @Then("the response should contain the updated user details")
    public void theResponseShouldContainUpdatedUser() {
        assertNotNull(response.jsonPath().get("name"), "name is null");
        assertNotNull(response.jsonPath().get("job"), "job is null");
        System.out.println("✅ Updated name: " + response.jsonPath().get("name"));
        System.out.println("✅ Updated job: " + response.jsonPath().get("job"));
    }
    @When("I delete user {int}")
    public void iDeleteAUser(int userId) {
        response = given()
                .when()
                .delete("/users/" + userId)
                .then()
                .extract()
                .response();

        System.out.println("✅ Delete user response status: " + response.getStatusCode());
    }
}