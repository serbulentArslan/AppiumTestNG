package base;

public class AppData {
    public static String platform = System.getProperty("platform", "android");
    public static String useElementWaitPlugin = System.getProperty("useElementWaitPlugin", "true");


}

//mvn clean test -Dplatform=android312412