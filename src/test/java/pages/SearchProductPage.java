package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchProductPage extends BasePage {

    private final By body = By.tagName("body");

    private final By explorePageMarker = By.xpath(
            "//*[contains(normalize-space(), 'Explore') or " +
                    "contains(normalize-space(), 'Cari produk investasi') or " +
                    "contains(normalize-space(), 'Saham') or " +
                    "contains(normalize-space(), 'Reksa Dana')]"
    );

    private final By searchInputByClass = By.cssSelector(
            "div[class*='bit-explore-input'] input"
    );

    private final By searchInputByPlaceholder = By.xpath(
            "//input[not(@type='hidden') and " +
                    "(contains(@placeholder,'Cari') or contains(@placeholder,'Search'))]"
    );

    private final By anyVisibleInput = By.xpath(
            "//input[not(@type='hidden')]"
    );

    private final By searchBoxTrigger = By.xpath(
            "//*[contains(@class,'bit-explore-input') or " +
                    "contains(normalize-space(), 'Cari produk investasi')]"
    );

    public SearchProductPage() {
        super();
    }

    public boolean isExplorePageDisplayed() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/explore"),
                    ExpectedConditions.visibilityOfElementLocated(explorePageMarker),
                    ExpectedConditions.visibilityOfElementLocated(searchInputByClass)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void searchInvestmentProduct(String keyword) {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/explore"),
                ExpectedConditions.visibilityOfElementLocated(explorePageMarker),
                ExpectedConditions.visibilityOfElementLocated(searchInputByClass)
        ));

        System.out.println("Current URL before search input: " + driver.getCurrentUrl());
        System.out.println("Page title before search input: " + driver.getTitle());
        System.out.println("Total input found before click: " + driver.findElements(By.tagName("input")).size());

        clickSearchBoxIfNeeded();

        System.out.println("Total input found after click: " + driver.findElements(By.tagName("input")).size());

        WebElement searchField = waitFirstVisible(
                searchInputByClass,
                searchInputByPlaceholder,
                anyVisibleInput
        );

        clearAndType(searchField, keyword);
        searchField.sendKeys(Keys.ENTER);

        waitForSearchResult(keyword);
    }

    private void clickSearchBoxIfNeeded() {
        try {
            WebElement trigger = waitFirstClickable(searchBoxTrigger, searchInputByClass);
            safeClick(trigger);
        } catch (Exception ignored) {
            // Input may already be visible. Continue to locating input.
        }
    }

    private void waitForSearchResult(String keyword) {
        wait.until(webDriver -> {
            String text = webDriver.findElement(body).getText().toLowerCase();

            if (text.contains("tidak ditemukan") || text.contains("not found")) {
                return true;
            }

            return containsRelatedText(text, keyword);
        });
    }

    public boolean isSearchResultRelatedTo(String keyword) {
        String pageText = waitVisible(body).getText();

        System.out.println("Page text after search:");
        System.out.println(pageText);

        String normalizedText = pageText.toLowerCase();

        if (normalizedText.contains("tidak ditemukan") || normalizedText.contains("not found")) {
            return false;
        }

        return containsRelatedText(normalizedText, keyword);
    }

    private boolean containsRelatedText(String text, String keyword) {
        String key = keyword.toLowerCase();

        if (key.contains("pasar uang")) {
            return text.contains("pasar uang")
                    || text.contains("money market")
                    || text.contains("rdpu")
                    || text.contains("reksa dana pasar uang");
        }

        if (key.contains("saham")) {
            return text.contains("saham")
                    || text.contains("equity")
                    || text.contains("reksa dana saham");
        }

        if (key.contains("obligasi")) {
            return text.contains("obligasi")
                    || text.contains("bond")
                    || text.contains("pendapatan tetap")
                    || text.contains("sbn");
        }

        return text.contains(key);
    }
}