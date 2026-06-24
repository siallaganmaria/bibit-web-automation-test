package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LandingPage extends BasePage {

    private final By root = By.id("root");

    private final By bibitLogoOrHeader = By.xpath(
            "//*[contains(normalize-space(), 'Bibit')] | " +
                    "//img[contains(@alt, 'Bibit') or contains(@src, 'bibit') or contains(@src, 'logo')]"
    );

    private final By searchIconByClass = By.cssSelector("div[class*='rightTopIcon']");

    private final By searchIconInsideTopBar = By.xpath(
            "//*[contains(@class,'bit-uikit-topbar-right')]//*[contains(@class,'rightTopIcon')]"
    );

    private final By exploreLinkFallback = By.cssSelector("a[href*='/explore']");

    public LandingPage() {
        super();
    }

    public void openBibitLandingPage() {
        driver.get("https://app.bibit.id/");
        waitForPageReady();
        waitVisible(root);
    }

    public boolean isLandingPageDisplayed() {
        try {
            waitFirstVisible(bibitLogoOrHeader, root);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSearchIconTopNavigation() {
        WebElement searchIcon = waitFirstClickable(
                searchIconByClass,
                searchIconInsideTopBar,
                exploreLinkFallback
        );

        System.out.println("Before click search icon URL: " + driver.getCurrentUrl());
        System.out.println("Search icon displayed: " + searchIcon.isDisplayed());
        System.out.println("Search icon enabled: " + searchIcon.isEnabled());

        safeClick(searchIcon);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/explore"),
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("div[class*='bit-explore-input'], input")
                )
        ));

        System.out.println("After click search icon URL: " + driver.getCurrentUrl());
    }
}