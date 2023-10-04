package tests.android;

import base.BaseTest;
import base.Util;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static driver.AppDriver.getCurrentDriver;

public class ScrollUntilYouFindListElement extends BaseTest {

    @Test
    public void scrollAndClick() {
        //android contacts app

        Util.scrollAndClick(By.xpath("//android.widget.ListView//android.view.ViewGroup//android.widget.TextView")
        ,"text","y1");

    }

    @Test
    public void scrollAndClickElement() {
        //android contacts app

        Util.scrollAndClick(By.xpath("//android.widget.TextView[@text='y1']"));
    }
}
