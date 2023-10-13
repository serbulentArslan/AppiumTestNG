package driver;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

public class AppDriver {
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static AppDriver instance;

    private AppDriver(){

    }
    public static AppDriver getInstance(){
        if(instance==null){
            instance = new AppDriver();
        }
        return instance;
    }

    public AppiumDriver getDriver(){
        return driver.get();
    }

    public static AppiumDriver getCurrentDriver(){
        return getInstance().getDriver();
    }

    public static void setDriver(AppiumDriver Driver){
        driver.set(Driver);
        System.out.println("Driver is set");
    }

}