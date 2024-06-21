import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pageObjects.ios.HomePage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class IOSBaseTest {

    public IOSDriver driver;
    public AppiumDriverLocalService service;
    public HomePage homePage;

    @BeforeClass
    public void configureAppium() throws URISyntaxException, MalformedURLException {

        String appiumJSPath = "C:\\Users\\abdullah.aydogan\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

        service = new AppiumServiceBuilder().withAppiumJS(new File(appiumJSPath))
            .withIPAddress("127.0.0.1").usingPort(4723).build();

        service.start();

        XCUITestOptions options = new XCUITestOptions();

        options.setDeviceName("iPhone 13 Pro");
        options.setApp("/UIKitCatalog.app");
        options.setPlatformVersion("15.5");
        options.setWdaLaunchTimeout(Duration.ofSeconds(20));

        driver = new IOSDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        homePage = new HomePage(driver);
    }

    @AfterClass
    public void tearDown() {

        driver.quit();
        service.stop();
    }
}