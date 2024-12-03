@store
Feature: Access to Petstore orders

  Background:
    Given Accept header is "application/json"
    And Content type header is "application/json"

  Scenario: Verify that the User can get all store inventory information
    When User send GET request "/store/inventory"
    Then Status code should be 200
    And Content type header is "application/json"
    And The following fields in the response body is not null
    |totvs|
    |sold|
    |available|
    |PENDING|

    Scenario: Verify that the User can post new order
      And User have request body
      When User send POST request "/store/order" endpoint
      Then Status code should be 200
      And Content type header is "application/json"
      And The response body should contain the order details



