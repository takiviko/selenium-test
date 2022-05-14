Feature: Add items to wishlist

  Background:
    Given the home page is opened

  Scenario Outline:
    When the same item is added to the wishlist '<numberOfClicks>' times
    Then I have '<numberOfItems>' items in my wishlist

    Examples:
      | numberOfClicks | numberOfItems |
      | 0              | 0             |
      | 1              | 1             |
      | 2              | 1             |
      | 10             | 1             |