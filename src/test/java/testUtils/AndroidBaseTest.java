package testUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pageObjects.android.FormPage;
import utils.AppiumUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class AndroidBaseTest extends AppiumUtils {

    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    public FormPage formPage;

    @BeforeClass
    public void configureAppium() throws IOException {

        String appPath = System.getProperty("user.dir") + "\\src\\test\\resources\\General-Store.apk";
        String propPath = System.getProperty("user.dir") + "\\src\\main\\resources\\data.properties";

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(propPath);
        prop.load(fis);

        String ipAddress = prop.getProperty("ipAddress");
        String port = prop.getProperty("port");
        service = startAppiumServer(ipAddress, Integer.parseInt(port));

        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName(prop.getProperty("androidDeviceName"));
        options.setApp(appPath);

        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        formPage = new FormPage(driver);
    }

    @AfterClass
    public void tearDown() {

        driver.quit();
        service.stop();
    }
}