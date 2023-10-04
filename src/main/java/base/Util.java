package base;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import static driver.AppDriver.getCurrentDriver;

public class Util {
    private static double SCROLL_RATIO = 0.5;
    private static Duration SCROLL_DUR = Duration.ofMillis(500);

    public static void scrollAndClick(By listItems, String attributeName, String text) {
        String previousPageSource = "";
        boolean flag = false;
        while (!isEndOfPage(previousPageSource)) {
            previousPageSource = getCurrentDriver().getPageSource();

            for (WebElement element : getCurrentDriver().findElements(listItems)) {

                if (element.getAttribute(attributeName).equalsIgnoreCase(text)) {
                    element.click();
                    flag = true; // come out of the for loop
                    break;
                }
            }
            if (flag)
                break; // come out of the while loop
            else
                scroll(ScrollDirection.DOWN, SCROLL_RATIO);
        }
    }

    public static void scrollAndClick(By byElement) {
        String previousPageSource = "";

        while (!isEndOfPage(previousPageSource)) {
            previousPageSource = getCurrentDriver().getPageSource();

            try {
                getCurrentDriver().findElement(byElement).click();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                scroll(ScrollDirection.DOWN, SCROLL_RATIO);
            }
        }
    }

    private static boolean isEndOfPage(String pageSource) {
        return pageSource.equals(getCurrentDriver().getPageSource());
    }

    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public static void scroll(ScrollDirection direction, double scrollRatio) {

        if (scrollRatio < 0 || scrollRatio > 1) {
            throw new Error("Scroll distance must be between 0 and 1.");
        }
        Dimension size = getCurrentDriver().manage().window().getSize();
        System.out.println(size);
        Point midPoint = new Point((int) (size.width * 0.5), (int) (size.height * 0.5));
        int bottom = midPoint.y + (int) (midPoint.y * scrollRatio);
        int top = midPoint.y - (int) (midPoint.y * scrollRatio);
        //Point start = new Point(midPoint.x, bottom );
        //Point end = new Point(midPoint.x, top );

        int left = midPoint.x - (int) (midPoint.x * scrollRatio);
        int right = midPoint.x + (int) (midPoint.x * scrollRatio);
        //Point start = new Point(right, midPoint.y );
        //Point end = new Point(left, midPoint.y );

        if (direction == ScrollDirection.UP) {
            swipe(new Point(midPoint.x, top), new Point(midPoint.x, bottom), SCROLL_DUR);
        } else if (direction == ScrollDirection.DOWN) {
            swipe(new Point(midPoint.x, bottom), new Point(midPoint.x, top), SCROLL_DUR);
        } else if (direction == ScrollDirection.LEFT) {
            swipe(new Point(left, midPoint.y), new Point(right, midPoint.y), SCROLL_DUR);
        } else {
            swipe(new Point(right, midPoint.y), new Point(left, midPoint.y), SCROLL_DUR);
        }


    }

    public static void swipe(Point start, Point end, Duration duration) {
        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(input.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getCurrentDriver().perform(ImmutableList.of(swipe));

    }

    public static void longPress(WebElement element) {
        Dimension sizeOfElement = element.getSize();
        Point locationOfElement = element.getLocation();

        Point centerOfElement = new Point(locationOfElement.getX() + sizeOfElement.width / 2,
                locationOfElement.getY() + sizeOfElement.height / 2);

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence swipe = new Sequence(input, 0);
        swipe.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement));
        swipe.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(new Pause(input, Duration.ofSeconds(1)));
        swipe.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getCurrentDriver().perform(ImmutableList.of(swipe));

    }

    public static Point getCenterOfElement(WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();

        return new Point(location.x + size.getWidth() / 2,
                location.y + size.getHeight() / 2);

    }

    public static void dragAndDrop(WebElement source, WebElement target) {
        Point centerOfSource = getCenterOfElement(source);
        Point centerOfTarget = getCenterOfElement(target);

        swipe(centerOfSource, centerOfTarget, SCROLL_DUR);

    }

    public static void zoomIn(WebElement pageSizeElement, int zoomInCount) {
        Point centerOfElement = getCenterOfElement(pageSizeElement);

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        Sequence sequence1 = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(),
                        centerOfElement.getX() + zoomInCount,
                        centerOfElement.getY() - zoomInCount))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence sequence2 = new Sequence(finger2, 1)
                .addAction(finger2.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger2, Duration.ofMillis(200)))
                .addAction(finger2.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(),
                        centerOfElement.getX() - zoomInCount,
                        centerOfElement.getY() + zoomInCount))
                .addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getCurrentDriver().perform(Arrays.asList(sequence1, sequence2));
    }

    public static void zoomOut(WebElement pageSizeElement, int zoomOutCount) {
        Point centerOfElement = getCenterOfElement(pageSizeElement);

        PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

        Sequence sequence1 = new Sequence(finger1, 1)
                .addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                        centerOfElement.getX() + zoomOutCount,
                        centerOfElement.getY() - zoomOutCount))
                .addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger1, Duration.ofMillis(200)))
                .addAction(finger1.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), centerOfElement))
                .addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence sequence2 = new Sequence(finger2, 1)
                .addAction(finger2.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                        centerOfElement.getX() - zoomOutCount,
                        centerOfElement.getY() + zoomOutCount))
                .addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger2, Duration.ofMillis(200)))
                .addAction(finger2.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(),centerOfElement))
                .addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getCurrentDriver().perform(Arrays.asList(sequence1, sequence2));
    }

    public static void drawing(WebElement drawPanel) {
        Point location = drawPanel.getLocation();
        Dimension size = drawPanel.getSize();

        Point sourcePoint = new Point(location.x + size.getWidth() / 2,
                location.y + 10);

        Point destinationPoint = new Point(location.x + size.getWidth() / 2,
                location.y + size.getHeight() - 10);

        swipe(sourcePoint, destinationPoint, SCROLL_DUR);
    }


    //gesture plugins methods
    public static void swipeLeft_gesturePlugin(WebElement carousel) {
        getCurrentDriver().executeScript("gesture: swipe", Map.of("elementId", ((RemoteWebElement) carousel).getId(), "percentage", 50, "direction", "left"));
    }

    public static void swipeRight_gesturePlugin(WebElement carousel) {
        getCurrentDriver().executeScript("gesture: swipe", Map.of("elementId", ((RemoteWebElement) carousel).getId(), "percentage", 50, "direction", "right"));
    }

    public static void swipeUp_gesturePlugin(WebElement carousel) {
        getCurrentDriver().executeScript("gesture: swipe", Map.of("elementId", ((RemoteWebElement) carousel).getId(), "percentage", 50, "direction", "up"));
    }

    public static void swipeDown_gesturePlugin(WebElement carousel) {
        getCurrentDriver().executeScript("gesture: swipe", Map.of("elementId", ((RemoteWebElement) carousel).getId(), "percentage", 50, "direction", "down"));
    }

    public static void dragAndDrop_gesturePlugin(WebElement source, WebElement target) {
        getCurrentDriver().executeScript("gesture: dragAndDrop", Map.of("sourceId", ((RemoteWebElement) source).getId(), "destinationId", ((RemoteWebElement) target).getId()));
    }

    public static void doubleTap_gesturePlugin(WebElement element) {
        getCurrentDriver().executeScript("gesture: doubleTap", Map.of("elementId", ((RemoteWebElement) element).getId()));
    }

    public static void longPress_gesturePlugin(WebElement element) {
        getCurrentDriver().executeScript("gesture: longPress", Map.of("elementId", ((RemoteWebElement) element).getId(), "pressure", 0.5, "duration", 800));
    }

}
