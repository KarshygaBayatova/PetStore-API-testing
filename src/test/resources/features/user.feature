@user
Feature: User functionality

  Background:
    Given Accept header is "application/json"

  Scenario Outline: Verify that the user can create user with "/user" endpoint
    And Content type header is "application/json"
    And Request body is "<id>", "<username>", "<firstName>", "<lastName>", "<email>", "<password>", "<phone>", "<userStatus>"
    When User send POST request "/user" endpoint
    Then Status code should be 200
    And Response Content type is "application/json"
    And code in the response body is 200
    Examples:
      | id | username | firstName | lastName | email           | password  | phone      | userStatus |
      | 6  | KimA     | Kim       | Adams    | kim@gmail.com   | kim123!   | 5718884142 | 0          |
      | 7  | AnnaS    | Anna      | Smith    | anna@gmail.com  | anna123!  | 7034123212 | 1          |
      | 8  | LeilaT   | Leila     | Tur      | leila@gmail.com | leila123! | 8551234565 | 1          |

  Scenario Outline: Verify that the newly created user can login to the system
    And Content type header is "application/json"
    And Path parameter is key "username" and value is "<username>"
    And Path parameter is key "password" and value is "<password>"
    When User send GET request "/user/login" endpoint
    Then Status code should be 200
    And Response Content type is "application/json"
    And code in the response body is 200
    And message in the response body contains "logged in user session:"
    Examples:
      | username | password  |
      | KimA     | kim123!   |
      | AnnaS    | anna123!  |
      | LeilaT   | leila123! |


  Scenario: Verify that the user can get user info by username "/user/{username}" endpoint
    And Path parameter is "username" and value is "KimA"
    When User send GET request "/user/{username}"
    Then Status code should be 200
    And Response Content type is "application/json"
    And User first name should be "Kim"
    And User last name should be "Adams"
    And User email should be "kim@gmail.com"
    And User password should be "kim123!"
    And User phone should be "5718884142"
    And User status should be 0

  Scenario Outline: Verify that the user can update the existing user
    And Content type header is "application/json"
    And Path parameter is "username" and value is "KimA"
    And Request body is "<id>", "<username>", "<firstName>", "<lastName>", "<email>", "<password>", "<phone>", "<userStatus>"
    When User send PUT request "/user/{username}" endpoint
    Then Status code should be 200
    And Response Content type is "application/json"
    And code in the response body is 200
    Examples:
      | id | username | firstName | lastName | email          | password | phone      | userStatus |
      | 6  | KimA     | Kim       | Smith    | kima@gmail.com | kim123!  | 5718884142 | 0          |

  Scenario: Verify that the user information updated
    And Path parameter is "username" and value is "KimA"
    When User send GET request "/user/{username}"
    Then Status code should be 200
    And Response Content type is "application/json"
    And User first name should be "Kim"
    And User last name should be "Smith"
    And User email should be "kima@gmail.com"
    And User password should be "kim123!"
    And User phone should be "5718884142"
    And User status should be 0

  Scenario: Verify that the user can delete the user by username
    And Path parameter is "username" and value is "AnnaS"
    When User send DELETE request "/user/{username}"
    Then Status code should be 200
    And Response Content type is "application/json"
    And code in the response body is 200
    And message in the response body is "AnnaS"

  Scenario: Verify that the user deleted
    And Path parameter is "username" and value is "AnnaS"
    When User send GET request "/user/{username}"
    Then Status code should be 404

  Scenario: Verify that the user logout from the system
    When User send GET request "/user/logout"
    Then Status code should be 200
    And code in the response body is 200
    And message in the response body is "ok"





