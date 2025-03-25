package seleniumTests.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class DriverManager {
    protected static WebDriver driver;

    public static WebDriver getWebDriver() {

        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--remote-allow-origins=*");
            ChromeDriver chromeDriver = new ChromeDriver(options);
            driver = chromeDriver;



        }
        return driver;
    }

    public static void disposeDriver() {
        driver.close();
        driver.quit();
        driver = null;
    }
}

