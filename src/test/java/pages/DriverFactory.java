package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class DriverFactory {

    private static WebDriver driver;

    public static void initDriver() {
        EdgeOptions options = new EdgeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        /*
         * Default: clean session for "without login" scenario.
         * Only use profile if you explicitly run:
         * -Dbibit.useProfile=true
         */
        boolean useProfile = Boolean.parseBoolean(
                System.getProperty("bibit.useProfile", "false")
        );

        String profilePath = System.getenv("BIBIT_EDGE_PROFILE");

        if (useProfile && profilePath != null && !profilePath.isEmpty()) {
            options.addArguments("--user-data-dir=" + profilePath);
        }

        driver = new EdgeDriver(options);
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized. Check Cucumber Hooks.");
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}