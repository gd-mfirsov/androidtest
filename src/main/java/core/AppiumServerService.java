package core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.IOException;
import java.util.Properties;

public class AppiumServerService {
    private String Windows_Node_JS_Path = "C:/Program Files/nodejs/node.exe";
    private String Windows_Appium_JS_Path = "C:/Program Files/nodejs/node_modules/appium/build/lib/main.js";
    private String Mac_Node_JS_Path = "/usr/local/opt/node@6/bin/node";
    private String Mac_Appium_JS_Path = "/usr/local/lib/node_modules/appium/build/lib/main.js";

    private AppiumDriverLocalService localService;

    public AppiumServerService() throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(AppiumServerService.class.getClassLoader().getResourceAsStream("appium.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (properties.getProperty("os.type")) {
            case "mac":
                System.setProperty(AppiumServiceBuilder.NODE_PATH, Mac_Node_JS_Path);
                System.setProperty(AppiumServiceBuilder.APPIUM_PATH, Mac_Appium_JS_Path);
                break;
            case "windows":
                System.setProperty(AppiumServiceBuilder.NODE_PATH, Windows_Node_JS_Path);
                System.setProperty(AppiumServiceBuilder.APPIUM_PATH, Windows_Appium_JS_Path);
                break;
            default:
                throw new Exception("Incorrect OS Type");
        }
        localService = AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService getLocalService() {
        return localService;
    }
}
