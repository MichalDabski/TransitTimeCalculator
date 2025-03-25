package seleniumTests.tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import seleniumTests.driver.DriverManager;
import seleniumTests.driver.DriverUtils;

import java.io.File;


public class TestBase {

    @BeforeMethod
    public void beforeTest() {
        DriverManager.getWebDriver();
        DriverUtils.setInitialConfiguration();
        DriverUtils.navigateToPage("https://www.dhl.com/se-en/home/freight/tools/european-road-freight-transit-time-calculator.html");
    }

    @AfterMethod
    void afterTest(ITestResult result) {

        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot ts = (TakesScreenshot) DriverManager.getWebDriver();
                File source = ts.getScreenshotAs(OutputType.FILE);
                try {
                    FileHandler.copy(source, new File("Screenshots/" + result.getName() + ".png"));
                    System.out.println("Screenshot taken");
                } catch (Exception e) {
                    System.out.println("Exception while taking screenshot " + e.getMessage());
                }
            } catch (WebDriverException e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    static void CleanUp() {
        System.out.println("Closing driver.");
        DriverManager.disposeDriver();
    }
}


