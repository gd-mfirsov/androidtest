package core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServerService {
    private String Windows_Node_JS_Path = "C:/Program Files/nodejs/node.exe";
    private String Windows_Appium_JS_Path = "C:/Program Files/nodejs/node_modules/appium/build/lib/main.js";

    private AppiumDriverLocalService localService;

    public AppiumServerService() {
        System.setProperty(AppiumServiceBuilder.NODE_PATH, Windows_Node_JS_Path);
        System.setProperty(AppiumServiceBuilder.APPIUM_PATH, Windows_Appium_JS_Path);
        localService = AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService getLocalService() {
        return localService;
    }
}
