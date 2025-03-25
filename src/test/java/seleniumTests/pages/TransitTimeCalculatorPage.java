package seleniumTests.pages;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import seleniumTests.Actions.SiteActions;
import seleniumTests.driver.DriverManager;
import seleniumTests.utilities.WaitUtilities;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class TransitTimeCalculatorPage {
    private final Logger logger = LogManager.getRootLogger();

    @FindBy(id = "onetrust-accept-btn-handler")
    protected WebElement btnAcceptCookies;
    @FindBy(id = "origin-country")
    protected WebElement drpOriginCountry;
    @FindBy(id = "origin-postcode")
    protected WebElement txtOriginPostcode;
    @FindBy(id = "destination-country")
    protected WebElement drpDestinationCountry;
    @FindBy(id = "destination-postcode")
    protected WebElement txtDestinationPostcode;
    @FindBy(xpath = "//span[text() ='Calculate']//parent::button")
    protected WebElement btnCalculate;

    @FindBy(xpath = "//p[@class='color-red-500 js--goto-previous-countryselector c-calculator--goto-previous c-calculator--text-align-right']/span")
    protected WebElement btnEditRoute;

    private final WebDriver driver;

    public TransitTimeCalculatorPage() {
        super();
        driver = DriverManager.getWebDriver();
        PageFactory.initElements(driver, this);
    }

    public TransitTimeCalculatorPage acceptAllCookies() {
        logger.info("Accepting all cookies");
        WaitUtilities.waitUntilElementIsClickable(btnAcceptCookies, 7);
        SiteActions.clickElement(btnAcceptCookies);
        SiteActions.scrollPage("400");
        return this;
    }

    public TransitTimeCalculatorPage provideIncorrectPostalCodes() {
        logger.info("Providing incorrect postal codes for both fields");
        txtOriginPostcode.clear();
        txtOriginPostcode.sendKeys("AB334");
        txtDestinationPostcode.clear();
        txtDestinationPostcode.sendKeys("MD777");
        return this;
    }

    public TransitTimeCalculatorPage assertErrorCodeIsVisible(String Label) {
        logger.info("Verifying if error message for " + Label + " postcode is visible");
        String errorMessage = DriverManager.getWebDriver().findElement(By.xpath("//input[@id='" + Label + "-postcode']//parent::div/p")).getText();
        logger.info("Error message displayed: " + errorMessage);
        Assert.assertEquals(errorMessage, "Correct postal code (e.g. no post box)*");
        return this;
    }

    public TransitTimeCalculatorPage provideOriginCountryData(String country, String postcode) {
        SiteActions.scrollPage("500");
        logger.info("Providing origin country data");
        WaitUtilities.waitUntilElementIsClickable(drpOriginCountry, 5);
        Select drpCountry = new Select(drpOriginCountry);
        drpCountry.selectByVisibleText(country);
        txtOriginPostcode.clear();
        txtOriginPostcode.sendKeys(postcode);
        return this;
    }

    public TransitTimeCalculatorPage provideDestinationDetails(String country, String postcode) {

        List<WebElement> options = new Select(drpDestinationCountry).getOptions();

        if ((options.size() == 1 && options.get(0).getText().equals("Sweden"))) {
            logger.info("Providing postal code for Sweden");
            txtDestinationPostcode.clear();
            txtDestinationPostcode.sendKeys("197 93");
        } else {
            logger.info("Providing destination country data");
            WaitUtilities.waitUntilElementIsClickable(drpDestinationCountry, 5);
            Select drpCountry = new Select(drpDestinationCountry);
            drpCountry.selectByVisibleText(country);
            txtDestinationPostcode.clear();
            txtDestinationPostcode.sendKeys(postcode);
        }
        return this;
    }

    public TransitTimeCalculatorPage calculateFreightTime() {
        logger.info("Clicking Calculate button");
        WaitUtilities.waitUntilElementIsClickable(btnCalculate, 5);
        SiteActions.clickElement(btnCalculate);
        WaitUtilities.waitUntilElementIsLoaded(btnCalculate, 15);
        return this;
    }

    public boolean deliveryDateIsProvided(String deliveryType) {
        WebElement date = DriverManager.getWebDriver().findElement(By.xpath("//strong[text() ='" + deliveryType + "']//ancestor::div/div[@class='l-grid--w-100pc-s c-leadtime--product-box-deliverydate']/p"));
        SiteActions.scrollToElement(date);
        String deliveryDate = date.getText();
        logger.info(deliveryType + " date is " + deliveryDate);
        boolean dateIsProvided = date.isDisplayed();
        return dateIsProvided;
    }

    public TransitTimeCalculatorPage editRoute() {
        logger.info("Editing route");
        try {
            SiteActions.scrollToElement(btnEditRoute);
        } catch (org.openqa.selenium.ElementNotInteractableException ignored) {
        }
        return this;
    }
}
