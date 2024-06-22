import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.android.CartPage;
import pageObjects.android.ProductCatalogue;
import testUtils.AndroidBaseTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ECommerce_TC_4_Hybrid extends AndroidBaseTest {

    @Test(dataProvider = "getData")
    public void fillForm(HashMap<String, String> input) throws InterruptedException {

        formPage.setNameField(input.get("name"));
        formPage.setGender(input.get("gender"));
        formPage.setCountrySelection(input.get("country"));

        ProductCatalogue productCatalogue = formPage.submitForm();

        productCatalogue.addItemToCartByIndex(0);
        productCatalogue.addItemToCartByIndex(0);

        CartPage cartPage = productCatalogue.goToCartPage();

        double totalSum = cartPage.getProductsSum();
        double displayFormattedSum = cartPage.getTotalAmountDisplayed();

        Assert.assertEquals(totalSum, displayFormattedSum);

        cartPage.acceptTermsConditions();
    }

    @AfterMethod
    public void backActivity() {

        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        String jsonPath = System.getProperty("user.dir") + "\\src\\test\\java\\testData\\eCommerce.json";

        List<HashMap<String, String>> data = getJsonData(jsonPath);
        return new Object[][] { {data.get(0)}, {data.get(1)} };
    }
}