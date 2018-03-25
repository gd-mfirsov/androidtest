package steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.qameta.allure.Feature;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.ProductPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("Appium test for GridU")
public class AppiumTests {

    private static AndroidDriver androidDriver;
    private MainPage mainPage;
    private ProductPage productPage;

    @BeforeClass
    public void beforeMethod() throws MalformedURLException {
        File appDir = new File("src");
        File app = new File(appDir, "Shopping List.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        capabilities.setCapability(MobileCapabilityType.APP, app);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        androidDriver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(androidDriver);
        productPage = new ProductPage(androidDriver);
    }

    @AfterClass
    public void releaseDriver() {
        androidDriver.quit();
    }

    @AfterMethod
    public void resetApp() {
        androidDriver.resetApp();
    }

    @Test(description = "Add Shopping List with few products", enabled = false)
    public void addNewShoppingList() {
        mainPage.addNewBuyList("Dummy one");

        assertThat(productPage.getCountOfItems(), is(0));

        productPage.setProductName("milk");
        productPage.setPrice(12.99);
        productPage.setAmount(5);
        productPage.setComment("This milk must be fresh");
        productPage.clickAddProduct();

        assertThat(productPage.getCountOfItems(), is(1));
        assertThat(productPage.getTotal(), containsString(Double.toString(64.95)));
        assertThat(productPage.getSpecifiedItemAmount("milk"), containsString(Integer.toString(5)));
        assertThat(productPage.getSpecifiedItemCost("milk"), containsString(Double.toString(12.99)));
        assertThat(productPage.getSpecifiedItemCommentText("milk"), is("This milk must be fresh"));

        productPage.setProductName("potato");
        productPage.setAmount(100);
        productPage.selectAmountType("kg.");
        productPage.clickAddProduct();

        assertThat(productPage.getCountOfItems(), is(2));
        assertThat(productPage.getTotal(), containsString(Double.toString(64.95)));
        assertThat(productPage.getSpecifiedItemCommentText("potato"), is(""));
    }

    @Test(description = "Add item in shopping list and then remove it")
    public void removeItemFromShoppingList() {
        mainPage.addNewBuyList("The first one");

        assertThat(productPage.getCountOfItems(), is(0));

        productPage.setProductName("icecream");
        productPage.setAmount(2);
        productPage.setPrice(4.99);
        productPage.selectCategory("Frozen food");
        productPage.clickAddProduct();

        productPage.deleteSpecifiedItem("icecream");

        assertThat(productPage.getCountOfItems(), is(0));
        assertThat(productPage.getTotal(), is("Total: 0 £"));
    }
}
