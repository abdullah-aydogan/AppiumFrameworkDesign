import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.android.FormPage;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ECommerce_TC_4_Hybrid extends BaseTest {

    @Test
    public void fillForm() throws InterruptedException {

        FormPage formPage = new FormPage(driver);

        formPage.setNameField("Rahul Shetty");
        formPage.setGender("female");
        formPage.setCountrySelection("Argentina");
        formPage.submitForm();

        driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
        driver.findElements(By.xpath("//android.widget.TextView[@text='ADD TO CART']")).get(0).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        // Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")),
            "text", "Cart")
        );

        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count = productPrices.size();
        double totalSum = 0;

        for(int i = 0; i < count; i++) {

            String amountString = productPrices.get(i).getText();
            double price = getFormattedAmount(amountString);
            totalSum += price;
        }

        String displaySum = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        double displayFormattedSum = getFormattedAmount(displaySum);

        Assert.assertEquals(totalSum, displayFormattedSum);

        WebElement ele = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longPressAction(ele);

        driver.findElement(By.id("android:id/button1")).click();
        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();

        Thread.sleep(6000);

        Set<String> contexts = driver.getContextHandles();

        for(String contextName : contexts) {

            System.out.println(contextName);
        }

        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("rahul shetty academy");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);

        Thread.sleep(3000);

        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP");
    }
}