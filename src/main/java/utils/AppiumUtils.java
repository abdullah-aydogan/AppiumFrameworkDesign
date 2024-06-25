package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class AppiumUtils {

    public AppiumDriverLocalService service;

    public double getFormattedAmount(String amount) {

        double price = Double.parseDouble(amount.substring(1));
        return price;
    }

    public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {

        String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();

        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<>() {


        });

        return data;
    }

    public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {

        String appiumJSPath = "C:\\Users\\abdullah.aydogan\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";

        service = new AppiumServiceBuilder().withAppiumJS(new File(appiumJSPath))
            .withIPAddress(ipAddress).usingPort(port).build();

        service.start();
        return service;
    }

    public String getScreenshotPath(String testCaseName, AppiumDriver driver) throws IOException {

        File source = driver.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "\\reports" + testCaseName + ".png";

        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }
}