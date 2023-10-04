package tests.android;

import base.BaseTest;
import base.Util;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static driver.AppDriver.getCurrentDriver;

public class DragAndDrop extends BaseTest {

    @Test
    public void dragAndDrop() throws InterruptedException {

        getCurrentDriver().findElement(AppiumBy.accessibilityId("Drag")).click();
        Thread.sleep(1000);

        WebElement source1 = getCurrentDriver().findElement(AppiumBy.accessibilityId("drag-l2"));
        WebElement target1 = getCurrentDriver().findElement(AppiumBy.accessibilityId("drop-l2"));

        //Util.dragAndDrop(source1,target1);
        for (int i=0; i<getSourceItems().size(); i++){

            Util.dragAndDrop_gesturePlugin(getEl(getSourceItems().get(i)),getEl(getTargetItems().get(i)));
            Thread.sleep(500);
        }


    }

    static ArrayList<String> getSourceItems() {
        ArrayList<String> sourceItems = new ArrayList<>();
        sourceItems.addAll(Arrays.asList("drag-l1", "drag-l2", "drag-l3",
                "drag-c1", "drag-c2", "drag-c3",
                "drag-r1", "drag-r2", "drag-r3"));
        return sourceItems;
    }
    static ArrayList<String> getTargetItems() {
        ArrayList<String> targetItems = new ArrayList<>();
        targetItems.addAll(Arrays.asList("drop-l1", "drop-l2", "drop-l3",
                "drop-c1", "drop-c2", "drop-c3",
                "drop-r1", "drop-r2", "drop-r3"));
        return targetItems;
    }
    static WebElement getEl(String item){
        return getCurrentDriver().findElement(AppiumBy.accessibilityId(item));

    }




}
