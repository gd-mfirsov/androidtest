package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.qameta.allure.Feature;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@Feature("Appium final test competition")
public class AppiumTests {

    private static AndroidDriver<AndroidElement> androidDriver;
    private LoginPage loginPage;

    @BeforeMethod
    public void beforeMethod() throws MalformedURLException {
        File appDir = new File("src");
        File app = new File(appDir, "MyPlate Calorie Tracker.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1");
        capabilities.setCapability(MobileCapabilityType.APP, app);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        androidDriver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"), capabilities);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(androidDriver);
    }

    @AfterMethod
    public void releaseDriver() {
        androidDriver.quit();
    }

    @Test(description = "Check that all button are displayed")
    public void checkThatButtonAreDisplayed() {
        assertThat(loginPage.isFacebookButtonDisplayed(), is(true));
        assertThat(loginPage.isLoginButtonDisplayed(), is(true));
        assertThat(loginPage.isSignUpButtonDisplayed(), is(true));
    }
}
