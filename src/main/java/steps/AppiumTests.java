package steps;

import core.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Pages;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("Appium test for GridU")
public class AppiumTests {

    private static AndroidDriver androidDriver;
    private DriverManager driverManager = new DriverManager();
    private Pages pages = new Pages();

    @BeforeClass
    public void beforeMethod() {
        androidDriver = driverManager.getAndroidDriver();
        androidDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        pages.getMainPage().addNewBuyList("Dummy one");

        assertThat(pages.getProductPage().getCountOfItems(), is(0));

        pages.getProductPage()
                .setProductName("milk")
                .setPrice(12.99)
                .setAmount(5)
                .setComment("This milk must be fresh")
                .clickAddProduct();

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getTotal(), containsString(Double.toString(64.95)));
        assertThat(pages.getProductPage().getSpecifiedItemAmount("milk"), is(5.0));
        assertThat(pages.getProductPage().getSpecifiedItemCost("milk"), is(12.99));
        assertThat(pages.getProductPage()
                .getSpecifiedItemCommentText("milk"), is("This milk must be fresh"));

        pages.getProductPage()
                .setProductName("potato")
                .setAmount(100)
                .selectAmountType("kg.")
                .clickAddProduct();

        assertThat(pages.getProductPage().getCountOfItems(), is(2));
        assertThat(pages.getProductPage().getTotal(), containsString(Double.toString(64.95)));
        assertThat(pages.getProductPage().getSpecifiedItemCommentText("potato"), is(""));
    }

    @Test(description = "Add item in shopping list and then remove it")
    public void removeItemFromShoppingList() {
        pages.getMainPage().addNewBuyList("The first one");

        assertThat(pages.getProductPage().getCountOfItems(), is(0));

        pages.getProductPage()
                .setProductName("icecream")
                .setAmount(2)
                .setPrice(4.99)
                .selectCategory("Frozen food")
                .clickAddProduct();

        pages.getProductPage().deleteSpecifiedItem("icecream");

        assertThat(pages.getProductPage().getCountOfItems(), is(0));
        assertThat(pages.getProductPage().getTotal(), is("Total: 0 £"));
    }

    @Test(description = "Make some products as bought")
    public void makeSomeProductsAsBought() {
        pages.getMainPage().addNewBuyList("The second one");

        pages.getProductPage()
                .setProductName("pasta")
                .setAmount(3)
                .setPrice(4.76)
                .clickAddProduct();

        pages.getProductPage()
                .setProductName("bread")
                .setPrice(2.4)
                .setAmount(1.5)
                .clickAddProduct();

        pages.getProductPage()
                .setProductName("salad")
                .setPrice(1.99)
                .setAmount(2)
                .selectAmountType("kg.")
                .clickAddProduct();

        assertThat(pages.getProductPage().getCountOfItems(), is(3));

        pages.getProductPage().setSpecifiedItemAsBought("bread");

        assertThat(pages.getProductPage().getCountOfItems(), is(3));
    }

    @Test(description = "Edit product without changes")
    public void editProductWithoutChanges() {
        pages.getMainPage().addNewBuyList("The third one");

        pages.getProductPage()
                .setProductName("marshmallow")
                .setPrice(3.99)
                .setAmount(10)
                .selectAmountType("pack")
                .clickAddProduct();

        pages.getProductPage().editSpecifiedItem("marshmallow");

        assertThat(pages.getProductPage().getAmountAsDouble(), is(10.0));
        assertThat(pages.getProductPage().getPriceAsDouble(), is(3.99));

        pages.getProductPage().clickSaveButtonOnEditItem();

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
    }

    @Test(description = "Change parameters while edit item")
    public void editProduct() {
        pages.getMainPage().addNewBuyList("The fourth one");

        pages.getProductPage()
                .setProductName("carrot")
                .setPrice(2.99)
                .setAmount(10)
                .selectAmountType("kg.")
                .clickAddProduct();

        pages.getProductPage()
                .editSpecifiedItem("carrot")
                .setAmount(8)
                .clickSaveButtonOnEditItem();

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getSpecifiedItemCost("carrot"), is(2.99));
        assertThat(pages.getProductPage().getSpecifiedItemAmount("carrot"), is(8.00));
    }

    @Test(description = "Change parameters on edit page and click Back button")
    public void discardChangesOnProduct() {
        pages.getMainPage().addNewBuyList("The fifth one");

        pages.getProductPage()
                .setProductName("cabbages")
                .setPrice(2.99)
                .setAmount(10)
                .selectAmountType("kg.")
                .clickAddProduct();

        pages.getProductPage()
                .editSpecifiedItem("cabbages")
                .setAmount(12)
                .clickBackButton();

        assertThat(pages.getDialogHelper().getDialogTitle(), is("Save"));
        assertThat(pages.getDialogHelper().getDialogMessage(), is("Save current item?"));

        pages.getDialogHelper().clickCancel();
        pages.getMainPage().clickOnShoppingListByIndex(0);

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getSpecifiedItemCost("cabbages"), is(2.99));
        assertThat(pages.getProductPage().getSpecifiedItemAmount("cabbages"), is(10.00));
    }

    @Test(description = "Set long name for product")
    public void setLongNameForItem() {
        pages.getMainPage().addNewBuyList("The eighth one");

        pages.getProductPage()
                .setProductName("The biggest name of the 2018 year")
                .setPrice(14.99)
                .setAmount(2)
                .selectAmountType("l")
                .clickAddProduct();

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getNameOfFirstProduct().length(),
                lessThan("The biggest name of the 2018 year".length()));
    }

    @Test(description = "Copy product from one list to another")
    public void copyProductFromOneListToAnother() {
        pages.getMainPage().addNewBuyList("The sixth one");

        pages.getProductPage()
                .setProductName("scissors")
                .setPrice(7.99)
                .setAmount(2)
                .selectAmountType("unit")
                .clickAddProduct();

        pages.getProductPage().clickBackButton();
        pages.getMainPage().addNewBuyList("The seventh one");
        pages.getProductPage().clickBackButton();
        pages.getMainPage().clickOnSpecifiedShoppingList("The sixth one");

        pages.getProductPage().clickCopyItem("scissors");
        pages.getDialogHelper().selectShoppingList("The seventh one");

        assertThat(pages.getProductPage().getCountOfItems(), is(1));

        pages.getProductPage().clickBackButton();

        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(2));

        pages.getMainPage().clickOnSpecifiedShoppingList("The seventh one");

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getTotal(), containsString(String.valueOf(2 * 7.99)));
    }

    @Test(description = "Copy product when exist only one Shopping List")
    public void copyProductWithOneShoppingList() {
        pages.getMainPage().addNewBuyList("The ninth one");

        pages.getProductPage().setProductName("coal");
        pages.getProductPage().clickAddProduct();

        pages.getProductPage().clickCopyItem("coal");

        assertThat(pages.getDialogHelper().getCountOfShoppingListsInDialog(), is(1));

        pages.getDialogHelper().selectShoppingList("The ninth one");

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
    }

    @Test(description = "Edit shopping list")
    public void editShoppingList() {
        pages.getMainPage().addNewBuyList("The tenth one");

        pages.getProductPage()
                .setProductName("toys")
                .clickAddProduct()
                .clickBackButton();

        pages.getMainPage().editShoppingListByIndex(0);
        pages.getDialogHelper().setEditShoppingListTB(" plus");

        assertThat(pages.getMainPage().getShoppingListName(), is("The tenth one plus"));
    }

    @Test(description = "Remove empty shopping list")
    public void removeEmptyShoppingList() {
        pages.getMainPage().addNewBuyList("The twelfth one");
        pages.getProductPage().clickBackButton();

        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(1));

        pages.getMainPage().removeShoppingListByIndex(0);

        assertThat(pages.getDialogHelper().getDialogTitle(), is("Delete"));
        assertThat(pages.getDialogHelper().getDialogMessage(), is("Are you sure?"));

        pages.getDialogHelper().clickOk();

        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(0));
    }

    @Test(description = "Remove list with products")
    public void removeShoppingListWithProducts() {
        pages.getMainPage().addNewBuyList("The thirteenth one");

        pages.getProductPage()
                .setProductName("onions")
                .setAmount(2.5)
                .setPrice(0.99)
                .selectAmountType("kg.")
                .clickAddProduct();

        pages.getProductPage().clickBackButton();

        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(1));

        pages.getMainPage().removeShoppingListByIndex(0);
        pages.getDialogHelper().clickOk();

        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(0));
    }
}
