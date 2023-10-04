package base;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;

public class AppiumServer {
    static AppiumDriverLocalService server;

    private static void setInstance(){
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder
                .withAppiumJS(new File("C:/Users/serars/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"))
                .usingDriverExecutable(new File("C:/Program Files/nodejs/node.exe"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                .withLogFile(new File("Appiumlog.txt"))
                .withIPAddress("127.0.0.1");

        if(AppData.useElementWaitPlugin.contains("true")){
            builder.withArgument(GeneralServerFlag.USE_PLUGINS, "element-wait");
        }

        //.withArgument(GeneralServerFlag.BASEPATH, "wd/hub")
        server = AppiumDriverLocalService.buildService(builder);
        //server.start();
        //System.out.println(server.getUrl());
        //System.out.println(server.isRunning());
        //server.stop();
    }

    private static AppiumDriverLocalService getInstance(){
        if(server == null){
            setInstance();
        }
        return server;
    }

    public static void start(){
        getInstance().start();
        System.out.println("Appium server started");
        System.out.println(server.getUrl());
        System.out.println(server.isRunning());
    }

    public static void stop(){
        if(server != null){
            getInstance().stop();
            System.out.println("Appium server stopped");
        }
    }
}