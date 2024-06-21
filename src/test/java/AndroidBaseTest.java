import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pageObjects.android.FormPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class AndroidBaseTest {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    public FormPage formPage;

    @BeforeClass
    public void configureAppium() throws URISyntaxException, MalformedURLException {

        String appiumJSPath = "C:\\Users\\abdullah.aydogan\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
        // String appPath = "C:\\Users\\abdullah.aydogan\\Desktop\\Appium Projeler\\AppiumDemo\\src\\test\\resources\\ApiDemos-debug.apk";
        String appPath = "C:\\Users\\abdullah.aydogan\\Desktop\\Appium Projeler\\AppiumDemo\\src\\test\\resources\\General-Store.apk";

        service = new AppiumServiceBuilder().withAppiumJS(new File(appiumJSPath))
            .withIPAddress("127.0.0.1").usingPort(4723).build();

        service.start();

        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName("Google Pixel 5");
        options.setApp(appPath);

        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        formPage = new FormPage(driver);
    }

    public void longPressAction(WebElement ele) {

        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) ele).getId(), "duration", 2000)
        );
    }

    public void scrollToEndAction() {

        boolean canScrollMore;

        do {
            canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture",
                    ImmutableMap.of("left", 100, "top", 100, "width", 200, "height", 200,
                            "direction", "down", "percent", 3.0
                    )
            );
        }

        while(canScrollMore);
    }

    public void swipeAction(WebElement ele, String direction) {

        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) ele).getId(),
                "direction", direction, "percent", 0.75, "speed", 5000
        ));
    }

    public double getFormattedAmount(String amount) {

        double price = Double.parseDouble(amount.substring(1));
        return price;
    }

    @AfterClass
    public void tearDown() {

        driver.quit();
        service.stop();
    }
}