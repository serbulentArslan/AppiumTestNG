package tests.android;

import base.BaseTest;
import base.Util;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static driver.AppDriver.getCurrentDriver;

public class LongPress extends BaseTest {

    @Test
    public void longPress() throws InterruptedException {
        //android phone app
        getCurrentDriver().findElement(AppiumBy.accessibilityId("key pad")).click();
        WebElement num0 = getCurrentDriver().findElement(AppiumBy.accessibilityId("0"));
        WebElement num8 = getCurrentDriver().findElement(AppiumBy.accessibilityId("8,TUV"));
        WebElement backSpace = getCurrentDriver().findElement(AppiumBy.accessibilityId("backspace"));

        Util.longPress(num0);
        num0.click();
        num8.click();
        num8.click();
        num8.click();
        num0.click();
        backSpace.click();
        Thread.sleep(2000);
        Util.longPress(backSpace);


    }
}
