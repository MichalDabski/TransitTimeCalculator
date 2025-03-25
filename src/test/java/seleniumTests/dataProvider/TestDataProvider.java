package seleniumTests.dataProvider;

import org.testng.annotations.DataProvider;

import java.util.Random;

public class TestDataProvider {

    private static final Object[][] COUNTRY_DATA = {
            {"Poland", "31232"},
            {"Germany", "12101"},
            {"France", "92100"},
            {"Spain", "28031"}
    };

    @DataProvider(name = "countryData")
    public static Object[][] getSingleCountryData() {
        Random rand = new Random();
        int index = rand.nextInt(COUNTRY_DATA.length);
        return new Object[][]{COUNTRY_DATA[index]};
    }
}
