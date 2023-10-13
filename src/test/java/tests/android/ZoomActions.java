package tests.android;

import base.BaseTest;
import base.Util;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static driver.AppDriver.getCurrentDriver;



public class ZoomActions extends BaseTest {

    @Test
    public void zoomIn() {
        getCurrentDriver().findElement(AppiumBy.accessibilityId("open menu")).click();
        getCurrentDriver().findElement(AppiumBy.accessibilityId("menu item drawing")).click();

        WebElement drawingPanel = getCurrentDriver().findElement(
                AppiumBy.xpath("//android.view.View[@resource-id='signature-pad']"));

        Util.zoomIn(drawingPanel,200);

        Assert.assertTrue(drawingPanel.isDisplayed());
    }

    @Test
    public void zoomOut() {
        getCurrentDriver().findElement(AppiumBy.accessibilityId("open menu")).click();
        getCurrentDriver().findElement(AppiumBy.accessibilityId("menu item drawing")).click();

        WebElement drawingPanel = getCurrentDriver().findElement(
                AppiumBy.xpath("//android.view.View[@resource-id='signature-pad']"));

        Util.zoomOut(drawingPanel,200);
    }


}
