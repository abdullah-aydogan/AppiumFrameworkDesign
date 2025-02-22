import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.ios.AlertViews;
import testUtils.IOSBaseTest;

public class IOSBasics extends IOSBaseTest {

    @Test
    public void iOSBasicsTest() {

        AlertViews alertViews = homePage.selectAlertViews();

        alertViews.fillTextLabel("hello");

        String actualMessage = alertViews.getConfirmMessage();
        Assert.assertEquals(actualMessage, "A message should be a short, complete sentence.");
    }
}