package seleniumTests.Actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import seleniumTests.driver.DriverManager;

public class SiteActions extends DriverManager {

    public static void scrollToElement(WebElement element) {
        new Actions(driver)
                .moveToElement(element)
                .perform();
    }

    public static void scrollPage(String pixel) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixel + ")");
    }

    public static void clickElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
}
