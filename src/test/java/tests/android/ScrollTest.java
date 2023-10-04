package tests.android;

import base.BaseTest;
import base.Util;
import com.google.common.collect.ImmutableList;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.annotations.Test;

import java.time.Duration;


import static base.Util.scroll;
import static driver.AppDriver.getCurrentDriver;


public class ScrollTest extends BaseTest {

    @Test
    public void scrollTest() throws InterruptedException {
        //webdriverio app
        getCurrentDriver().findElement(AppiumBy.accessibilityId("Swipe")).click();
        Thread.sleep(300);

        Util.scroll(Util.ScrollDirection.DOWN, 0.5);
        Thread.sleep(2000);
        Util.scroll(Util.ScrollDirection.UP, 0.5);
        Thread.sleep(2000);
        Util.scroll(Util.ScrollDirection.RIGHT, 0.5);
        Thread.sleep(2000);
        Util.scroll(Util.ScrollDirection.LEFT, 0.5);
    }


}
