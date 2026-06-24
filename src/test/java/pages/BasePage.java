package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForPageReady() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    protected WebElement waitFirstVisible(By... locators) {
        return wait.until(webDriver -> {
            for (By locator : locators) {
                List<WebElement> elements = webDriver.findElements(locator);

                for (WebElement element : elements) {
                    try {
                        if (element.isDisplayed()
                                && element.getSize().getHeight() > 0
                                && element.getSize().getWidth() > 0) {
                            return element;
                        }
                    } catch (StaleElementReferenceException ignored) {
                    }
                }
            }
            return null;
        });
    }

    protected WebElement waitFirstClickable(By... locators) {
        return wait.until(webDriver -> {
            for (By locator : locators) {
                List<WebElement> elements = webDriver.findElements(locator);

                for (WebElement element : elements) {
                    try {
                        if (element.isDisplayed()
                                && element.isEnabled()
                                && element.getSize().getHeight() > 0
                                && element.getSize().getWidth() > 0) {
                            return element;
                        }
                    } catch (StaleElementReferenceException ignored) {
                    }
                }
            }
            return null;
        });
    }

    protected void safeClick(WebElement element) {
        scrollIntoView(element);

        try {
            element.click();
        } catch (ElementClickInterceptedException | JavascriptException | StaleElementReferenceException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void clearAndType(WebElement element, String text) {
        element.click();

        try {
            element.clear();
        } catch (Exception ignored) {
            element.sendKeys(Keys.CONTROL + "a");
            element.sendKeys(Keys.BACK_SPACE);
        }

        element.sendKeys(text);
    }

    protected String getPageText() {
        return waitVisible(By.tagName("body")).getText();
    }

    protected boolean pageContainsAny(String... expectedTexts) {
        String text = getPageText().toLowerCase();

        for (String expected : expectedTexts) {
            if (text.contains(expected.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}