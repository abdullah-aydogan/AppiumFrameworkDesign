package pageObjects.ios;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.IOSActions;

public class AlertViews extends IOSActions {

    IOSDriver driver;

    public AlertViews(IOSDriver driver) {

        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`label == 'Text Entry'`]")
    private WebElement textEntryMenu;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeStaticText' AND value == BEGINSWITH[c] 'Confirm'")
    private WebElement confirmMenuItem;

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeCell")
    private WebElement textBox;

    @iOSXCUITFindBy(accessibility = "OK")
    private WebElement acceptPopup;

    @iOSXCUITFindBy(iOSNsPredicate = "name BEGINSWITH[c] 'A message'")
    private WebElement confirmMessage;

    @iOSXCUITFindBy(iOSNsPredicate = "label == 'Confirm'")
    private WebElement submit;

    public void fillTextLabel(String name) {

        textEntryMenu.click();
        textBox.sendKeys(name);
        acceptPopup.click();
    }

    public String getConfirmMessage() {

        confirmMenuItem.click();
        return confirmMessage.getText();
    }
}