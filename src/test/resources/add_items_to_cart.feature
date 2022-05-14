Feature: Add items to cart

  Background:
    Given the home page is opened

    Scenario Outline:
      When and item is added to the cart '<numberOfClicks>' times
      Then I have '<numberOfItems>' items in my cart

      Examples:
        | numberOfClicks | numberOfItems |
        | 0              | 0             |
        | 1              | 1             |
        | 2              | 2             |
        | 5              | 5             |