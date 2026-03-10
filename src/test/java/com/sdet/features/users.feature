Feature: User API Tests
  As a tester
  I want to verify the Users API
  So that I can ensure it returns correct data

  Scenario: Get list of users successfully
    Given the API is available
    When I request the list of users on page 1
    Then the response status code should be 200
    And the response should contain a list of users
    And each user should have an id, email and first name

  Scenario: Get users on page 2
    Given the API is available
    When I request the list of users on page 2
    Then the response status code should be 200
    And the response should contain a list of users
    
  Scenario: Create a new user successfully
  Given the API is available
  When I create a user with name "John" and job "SDET"
  Then the response status code should be 201
  And the response should contain the created user details
  
  Scenario: Update an existing user successfully
  Given the API is available
  When I update user 1 with name "John Updated" and job "Senior SDET"
  Then the response status code should be 200
  And the response should contain the updated user details
  
  Scenario: Delete an existing user successfully
  Given the API is available
  When I delete user 1
  Then the response status code should be 200  