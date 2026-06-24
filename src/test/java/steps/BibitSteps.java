package steps;

import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.LandingPage;
import pages.SearchProductPage;

public class BibitSteps {

    private LandingPage landingPage;
    private SearchProductPage searchProductPage;

    @Given("user is on Bibit landing page")
    public void userIsOnBibitLandingPage() {
        landingPage = new LandingPage();
        landingPage.openBibitLandingPage();

        Assert.assertTrue(
                "Bibit landing page is not displayed.",
                landingPage.isLandingPageDisplayed()
        );
    }

    @When("user clicks Search icon on top navigation")
    public void userClicksSearchIconOnTopNavigation() {
        landingPage.clickSearchIconTopNavigation();

        searchProductPage = new SearchProductPage();

        Assert.assertTrue(
                "Explore page is not displayed after clicking search icon.",
                searchProductPage.isExplorePageDisplayed()
        );
    }

    @When("user searches investment product {string}")
    public void userSearchesInvestmentProduct(String keyword) {
        searchProductPage.searchInvestmentProduct(keyword);
    }

    @Then("search result should show product related to {string}")
    public void searchResultShouldShowProductRelatedTo(String keyword) {
        Assert.assertTrue(
                "Search result does not show product related to: " + keyword,
                searchProductPage.isSearchResultRelatedTo(keyword)
        );
    }
}