@smoke
Feature: Login features

  Background:
    Given the user navigates to the login page


  Scenario: the user should able to log in with valid credentials
    When the user enters email
    And the user enters password
    And the user clicks on the Sign in button
    Then the user logs in and lands on "Weavr - Innovator Portal" page
    Then the user logs in with "email", "password" and status code is 200


  Scenario Outline: the user can not able to log in with invalid credentials (Negative Scenario)
    When the user enters "<email>"
    And the user enters the "<password>"
    And the user clicks on the Sign in button
    Then the user can not be able to log in and the following message should be displayed
      | Invalid Credentials |
    Then the user can not be able log in with invalid "<email>", "<password>" and status code is 409
    Examples:
      | email        |  | password        |
      | invalidEmail |  | password        |
      | email        |  | invalidPassword |
      | invalidEmail |  | invalidPassword |
