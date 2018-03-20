package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.qameta.allure.Feature;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UpdateProfilePage;
import pages.helpers.LoginHelper;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Feature("Appium final test competition")
public class AppiumTests {

    private static AndroidDriver<AndroidElement> androidDriver;
    private LoginPage loginPage;
    private UpdateProfilePage profilePage;
    private LoginHelper loginHelper;

    @BeforeMethod
    public void beforeMethod() throws MalformedURLException {
        File appDir = new File("src");
        File app = new File(appDir, "MyPlate Calorie Tracker.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        capabilities.setCapability(MobileCapabilityType.APP, app);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        androidDriver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"), capabilities);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(androidDriver);
        profilePage = new UpdateProfilePage(androidDriver);
        loginHelper = new LoginHelper(androidDriver);
    }

    @AfterMethod
    public void releaseDriver() {
        androidDriver.quit();
    }

    @Test(description = "Check that all button are displayed", enabled = false)
    public void checkThatButtonAreDisplayed() {
        assertThat(loginPage.isFacebookButtonDisplayed(), is(true));
        assertThat(loginPage.isLoginButtonDisplayed(), is(true));
        assertThat(loginPage.isSignUpButtonDisplayed(), is(true));
    }

    @Test(description = "Update profile data")
    public void checkLoginViaFacebook() {
        loginPage.clickLoginViaEmail();
        loginHelper.performLogin("chose@bk.ru", "jva6gn45n");

        assertThat(profilePage.getGenderText(), is("Male"));
        assertThat(profilePage.getBirthdayText(), is("Mar 19, 1993"));
        assertThat(profilePage.getActivityText(), is("Sedentary"));
        assertThat(profilePage.getHeightText(), is("6 ft 3 in"));
        assertThat(profilePage.getWeightText(), is("209.44 lb"));
        assertThat(profilePage.getGoalWeightText(), is("187.39 lb"));

        profilePage.clickGender();
        profilePage.selectOption("Female");
        profilePage.setBirthday("Jun", 12, 1998);
        profilePage.clickActivity();
        profilePage.selectOption("Moderately Active");
        profilePage.setHeight("Centimeters", 204);
        profilePage.setWeight("Kilograms", 85);
        profilePage.setGoalWeight("Kilograms", 80);

        assertThat(profilePage.getGenderText(), is("Female"));
        assertThat(profilePage.getBirthdayText(), is("Jun 12, 1998"));
        assertThat(profilePage.getActivityText(), is("Moderately Active"));
        assertThat(profilePage.getHeightText(), is("204.0 cm"));
        assertThat(profilePage.getWeightText(), is("85.0 kg"));
        assertThat(profilePage.getGoalWeightText(), is("80.0 kg"));
    }
}
