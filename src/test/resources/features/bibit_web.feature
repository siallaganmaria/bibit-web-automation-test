Feature: Bibit Web - Search Product Investasi

  @search_product_from_search_icon
  Scenario Outline: Search product invest from search icon without login
    Given user is on Bibit landing page
    When user clicks Search icon on top navigation
    And user searches investment product "<keyword>"
    Then search result should show product related to "<keyword>"

    Examples:
      | keyword    |
      | Pasar Uang |
      | Saham      |
      | Obligasi   |