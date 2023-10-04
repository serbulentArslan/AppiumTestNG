package tests.android;

import base.BaseTest;
import base.Util;
import driver.AppDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static driver.AppDriver.getCurrentDriver;

public class Drawing extends BaseTest {

    @Test
    public void drawing() {

        getCurrentDriver().findElement(AppiumBy.accessibilityId("open menu")).click();
        getCurrentDriver().findElement(AppiumBy.accessibilityId("menu item drawing")).click();

        WebElement drawingPanel = getCurrentDriver().findElement(
                AppiumBy.xpath("//android.view.View[@resource-id='signature-pad']"));

        Util.drawing(drawingPanel);

    }
}
