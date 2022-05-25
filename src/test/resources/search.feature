Feature: Typing something in the search bar and clicking search takes the user to the search page with the correct query parameters

  Scenario Outline:
    Given the home page is opened
    When the user enters '<text>' into the search bar
    And The user clicks the search button
    Then the user gets taken to the search page with '<text>' as the search parameter

    Examples:
      | text   |
      | iPhone |
      | laptop |
      | 30"    |
      |        |