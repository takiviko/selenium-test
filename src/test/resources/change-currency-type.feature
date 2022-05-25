Feature: Selecting a currency from the currency dropdown sets the currently used currency

  Scenario Outline:
    Given the home page is opened
    And the currency dropdown is clicked
    When the '<currency-type>' currency option is selected
    Then the currency type switches to '<currency-type>'
    And prices are displayed in '<currency-type>'
    And the cart has its currency set to '<currency-type>'
    Examples:
      | currency-type |
      | $             |
      | £             |
      | €             |