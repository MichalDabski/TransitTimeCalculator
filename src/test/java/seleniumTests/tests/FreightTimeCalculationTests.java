package seleniumTests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import seleniumTests.dataProvider.TestDataProvider;
import seleniumTests.pages.TransitTimeCalculatorPage;

public class FreightTimeCalculationTests extends TestBase {

    @Test(priority = 1)
    public void verifyPostalCodeFieldValidation() {
        TransitTimeCalculatorPage calculatorPage = new TransitTimeCalculatorPage();
        calculatorPage.acceptAllCookies()
                .provideIncorrectPostalCodes()
                .calculateFreightTime()
                .assertErrorCodeIsVisible("origin")
                .assertErrorCodeIsVisible("destination");
    }

    @Test(priority = 2, dataProvider = "countryData", dataProviderClass = TestDataProvider.class)
    public void calculateDeliveryForSwedenAsDestination(String country, String postcode) {
        TransitTimeCalculatorPage calculatorPage = new TransitTimeCalculatorPage();
        calculatorPage.provideOriginCountryData(country, postcode)
                .provideDestinationDetails(country, postcode)
                .calculateFreightTime();
        Assert.assertTrue(calculatorPage.deliveryDateIsProvided("DHL Freight Eurapid"));
        Assert.assertTrue(calculatorPage.deliveryDateIsProvided("DHL Freight EuroConnect"));
    }

    @Test(priority = 3, dataProvider = "countryData", dataProviderClass = TestDataProvider.class)
    public void calculateDeliveryForSwedenAsOrigin(String country, String postcode) {
        TransitTimeCalculatorPage calculatorPage = new TransitTimeCalculatorPage();
        calculatorPage.editRoute()
                .provideOriginCountryData("Sweden", " 197 93")
                .provideDestinationDetails(country, postcode)
                .calculateFreightTime();
        Assert.assertTrue(calculatorPage.deliveryDateIsProvided("DHL Freight Eurapid"));
        Assert.assertTrue(calculatorPage.deliveryDateIsProvided("DHL Freight EuroConnect"));
    }
}
