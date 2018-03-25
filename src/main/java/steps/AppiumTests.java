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
import pages.DialogHelper;
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
    private DialogHelper dialogHelper;

    @BeforeClass
    public void beforeMethod() throws MalformedURLException {
        File appDir = new File("src");
        File app = new File(appDir, "Shopping List.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
        capabilities.setCapability(MobileCapabilityType.APP, app);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        androidDriver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(androidDriver);
        productPage = new ProductPage(androidDriver);
        dialogHelper = new DialogHelper(androidDriver);
    }

    @AfterClass
    public void releaseDriver() {
        androidDriver.quit();
    }

    @AfterMethod
    public void resetApp() {
        androidDriver.resetApp();
    }

    @Test(description = "Add Shopping List with few products")
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
        assertThat(productPage.getSpecifiedItemAmount("milk"), is(5.0));
        assertThat(productPage.getSpecifiedItemCost("milk"), is(12.99));
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

    @Test(description = "Make some products as bought")
    public void makeSomeProductsAsBought() {
        mainPage.addNewBuyList("The second one");

        productPage.setProductName("pasta");
        productPage.setAmount(3);
        productPage.setPrice(4.76);
        productPage.clickAddProduct();

        productPage.setProductName("bread");
        productPage.setPrice(2.4);
        productPage.setAmount(1.5);
        productPage.clickAddProduct();

        productPage.setProductName("salad");
        productPage.setPrice(1.99);
        productPage.setAmount(2);
        productPage.selectAmountType("kg.");
        productPage.clickAddProduct();

        assertThat(productPage.getCountOfItems(), is(3));

        productPage.setSpecifiedItemAsBought("bread");

        assertThat(productPage.getCountOfItems(), is(3));
    }

    @Test(description = "Edit product without changes")
    public void editProductWithoutChanges() {
        mainPage.addNewBuyList("The third one");

        productPage.setProductName("marshmallow");
        productPage.setPrice(3.99);
        productPage.setAmount(10);
        productPage.selectAmountType("pack");
        productPage.clickAddProduct();

        productPage.editSpecifiedItem("marshmallow");

        assertThat(productPage.getAmountAsDouble(), is(10.0));
        assertThat(productPage.getPriceAsDouble(), is(3.99));

        productPage.clickSaveButtonOnEditItem();

        assertThat(productPage.getCountOfItems(), is(1));
    }

    @Test(description = "Change parameters while edit item")
    public void editProduct() {
        mainPage.addNewBuyList("The fourth one");

        productPage.setProductName("carrot");
        productPage.setPrice(2.99);
        productPage.setAmount(10);
        productPage.selectAmountType("kg.");
        productPage.clickAddProduct();

        productPage.editSpecifiedItem("carrot");
        productPage.setAmount(8);
        productPage.clickSaveButtonOnEditItem();

        assertThat(productPage.getCountOfItems(), is(1));
        assertThat(productPage.getSpecifiedItemCost("carrot"), is(2.99));
        assertThat(productPage.getSpecifiedItemAmount("carrot"), is(8.00));
    }

    @Test(description = "Change parameters on edit page and click Back button")
    public void discardChangesOnProduct() {
        mainPage.addNewBuyList("The fifth one");

        productPage.setProductName("cabbages");
        productPage.setPrice(2.99);
        productPage.setAmount(10);
        productPage.selectAmountType("kg.");
        productPage.clickAddProduct();

        productPage.editSpecifiedItem("cabbages");
        productPage.setAmount(12);
        productPage.clickBackButton();

        assertThat(dialogHelper.getDialogTitle(), is("Save"));
        assertThat(dialogHelper.getDialogMessage(), is("Save current item?"));

        dialogHelper.clickCancel();
        mainPage.clickOnShoppingListByIndex(0);

        assertThat(productPage.getCountOfItems(), is(1));
        assertThat(productPage.getSpecifiedItemCost("cabbages"), is(2.99));
        assertThat(productPage.getSpecifiedItemAmount("cabbages"), is(10.00));
    }
}
