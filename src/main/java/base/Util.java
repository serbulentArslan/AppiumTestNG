package base;

import com.google.common.collect.ImmutableList;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;

import static driver.AppDriver.getCurrentDriver;

public class Util {
    private static double SCROLL_RATIO = 0.5;

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
        Duration SCROLL_DUR = Duration.ofMillis(600);
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
}
