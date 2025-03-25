package seleniumTests.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTests.driver.DriverManager;

import java.time.Duration;

public class WaitUtilities {

    private static WebDriverWait getWebDriverWait(int seconds) {
        return new WebDriverWait(DriverManager.getWebDriver(), Duration.ofSeconds(seconds));
    }

    private final WebDriver driver;

    public WaitUtilities() {
        super();
        driver = DriverManager.getWebDriver();
        PageFactory.initElements(driver, this);
    }

    public static void waitUntilElementIsVisible(WebElement element, int sec) {
        WebDriverWait webDriverWait = getWebDriverWait(sec);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilElementIsClickable(WebElement element, int sec) {
        WebDriverWait webDriverWait = getWebDriverWait(sec);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilElementIsLoaded(WebElement element, int sec) {
        WebDriverWait webDriverWait = getWebDriverWait(sec);
        webDriverWait.until(ExpectedConditions.not(
                ExpectedConditions.attributeContains(element, "class", "is-loading")
        ));
    }
}



