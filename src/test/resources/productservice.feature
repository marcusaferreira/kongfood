Feature: Verify the product service

  Scenario: Customer makes call to GET product
    When I make a GET request to "/products/category/drinks
    Then the response status should be "200"
    And the response body should be a json object with the following properties:
      | id | name | price | category | description