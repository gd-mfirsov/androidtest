package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverManager {
    private static AndroidDriver androidDriver;
    private static AppiumDriver appiumDriver;
    private static IOSDriver iosDriver;

    public AndroidDriver getAndroidDriver() {
        Properties properties = new Properties();
        try {
            properties.load(DriverManager.class.getClassLoader().getResourceAsStream("appium.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (androidDriver == null) {
            try {
                androidDriver = new AndroidDriver(new URL(properties.getProperty("appium.server.url") + "/wd/hub"),
                        getAndroidCapabilities());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return androidDriver;
    }

    public AppiumDriver getAppiumDriver() throws MalformedURLException {
        Properties properties = new Properties();
        try {
            properties.load(DriverManager.class.getClassLoader().getResourceAsStream("appium.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (appiumDriver == null) {
            appiumDriver = new AppiumDriver(new URL(properties.getProperty("appium.server.url") + "/wd/hub"),
                    getAndroidCapabilities());
        }
        return appiumDriver;
    }

    private static Capabilities getAndroidCapabilities() {
        File appDir = new File("src");
        File app = new File(appDir, "Shopping List.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        capabilities.setCapability(MobileCapabilityType.APP, app);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        return capabilities;
    }
}
