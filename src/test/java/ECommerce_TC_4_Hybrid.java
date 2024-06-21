import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.CartPage;
import pageObjects.android.ProductCatalogue;

public class ECommerce_TC_4_Hybrid extends AndroidBaseTest {

    @Test
    public void fillForm() throws InterruptedException {

        formPage.setNameField("Rahul Shetty");
        formPage.setGender("female");
        formPage.setCountrySelection("Argentina");

        ProductCatalogue productCatalogue = formPage.submitForm();

        productCatalogue.addItemToCartByIndex(0);
        productCatalogue.addItemToCartByIndex(0);

        CartPage cartPage = productCatalogue.goToCartPage();

        double totalSum = cartPage.getProductsSum();
        double displayFormattedSum = cartPage.getTotalAmountDisplayed();

        Assert.assertEquals(totalSum, displayFormattedSum);

        cartPage.acceptTermsConditions();
        cartPage.submitOrder();
    }
}