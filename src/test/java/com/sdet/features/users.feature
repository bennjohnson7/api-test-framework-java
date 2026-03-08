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