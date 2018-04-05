package steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.AppiumServerService;
import core.DriverManager;
import core.Retry;
import data.DataTestObject;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Feature;
import org.testng.annotations.*;
import pages.Pages;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Feature("Appium test for GridU")
public class AppiumTests {

    private static AndroidDriver androidDriver;
    private DriverManager driverManager = new DriverManager();
    private Pages pages = new Pages();
    private AppiumServerService serverService = new AppiumServerService();
    private ObjectMapper objectMapper = new ObjectMapper();
    private DataTestObject testObject;

    private static String DIALOG_SAVE_TITLE = "Save";
    private static String DIALOG_SAVE_MESSAGE = "Save current item?";
    private static String DIALOG_DELETE_TITLE = "Delete";
    private static String DIALOG_DELETE_MESSAGE = "Are you sure?";

    public AppiumTests() throws Exception {
    }

    @BeforeTest
    public void beforeTest() {
        serverService.getLocalService().start();
    }

    @BeforeClass
    public void beforeClass() {
        androidDriver = driverManager.getAndroidDriver();
        try {
            testObject = objectMapper.readValue(new File("testdata/test_data.json"), DataTestObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void beforeMethod() {
        pages.getMainPage().addNewBuyList(testObject.getLists().get(0).getShoppingList());
    }

    @AfterClass
    public void releaseDriver() {
        androidDriver.quit();
    }

    @AfterMethod
    public void resetApp() {
        androidDriver.resetApp();
    }

    @AfterTest
    public void afterTest() {
        serverService.getLocalService().stop();
    }

    @Test(description = "Add Shopping List with few products", retryAnalyzer = Retry.class)
    public void addNewShoppingList() {

        assertThat(pages.getProductPage().getCountOfItems(), is(0));

        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .setComment(testObject.getLists().get(0).getProducts().get(0).getComment())
                .clickAddProduct();

        int countOfItems = pages.getProductPage().getCountOfItems();
        double total = pages.getProductPage().getTotal();
        double itemAmount = pages.getProductPage().getSpecifiedItemAmount(testObject.getLists().get(0)
                .getProducts().get(0).getName());
        double itemCost = pages.getProductPage().getSpecifiedItemCost(testObject.getLists().get(0)
                .getProducts().get(0).getName());
        String itemComment = pages.getProductPage()
                .getSpecifiedItemCommentText(testObject.getLists().get(0).getProducts().get(0).getName());

        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(1).getName())
                .setAmount(testObject.getLists().get(0).getProducts().get(1).getAmount())
                .selectAmountType(testObject.getLists().get(0).getProducts().get(1).getAmountType())
                .clickAddProduct();

        assertThat(countOfItems, is(1));
        assertThat(total, is(testObject.getLists().get(0).getProducts().get(0).getPrice() *
                testObject.getLists().get(0).getProducts().get(0).getAmount()));
        assertThat(itemAmount, is(testObject.getLists().get(0).getProducts().get(0).getAmount()));
        assertThat(itemCost, is(testObject.getLists().get(0).getProducts().get(0).getPrice()));
        assertThat(itemComment, is(testObject.getLists().get(0).getProducts().get(0).getComment()));

        assertThat(pages.getProductPage().getCountOfItems(), is(2));
        assertThat(pages.getProductPage().getTotal(),
                is(testObject.getLists().get(0).getProducts().get(0).getPrice() *
                testObject.getLists().get(0).getProducts().get(0).getAmount()));
        assertThat(pages.getProductPage()
                .getSpecifiedItemCommentText(testObject.getLists().get(0).getProducts().get(1).getName()),
                is(""));
    }

    @Test(description = "Add item in shopping list and then remove it", retryAnalyzer = Retry.class)
    public void removeItemFromShoppingList() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .selectCategory(testObject.getLists().get(0).getProducts().get(0).getCategory())
                .clickAddProduct();

        pages.getProductPage().deleteSpecifiedItem(testObject.getLists().get(0).getProducts().get(0).getName());

        assertThat(pages.getProductPage().getCountOfItems(), is(0));
        assertThat(pages.getProductPage().getTotal(), is(0));
    }

    @Test(description = "Make some products as bought", retryAnalyzer = Retry.class)
    public void makeSomeProductsAsBought() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .clickAddProduct();

        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(1).getName())
                .setPrice(testObject.getLists().get(0).getProducts().get(1).getPrice())
                .setAmount(testObject.getLists().get(0).getProducts().get(1).getAmount())
                .clickAddProduct();

        pages.getProductPage()
                .setProductName(testObject.getLists().get(1).getProducts().get(0).getName())
                .setPrice(testObject.getLists().get(1).getProducts().get(0).getPrice())
                .setAmount(testObject.getLists().get(1).getProducts().get(0).getAmount())
                .selectAmountType(testObject.getLists().get(1).getProducts().get(0).getAmountType())
                .clickAddProduct();

        int itemsCount = pages.getProductPage().getCountOfItems();

        pages.getProductPage().setSpecifiedItemAsBought(testObject.getLists().get(0).getProducts().get(1).getName());

        assertThat(itemsCount, is(3));
        assertThat(pages.getProductPage().getCountOfItems(), is(3));
    }

    @Test(description = "Edit product without changes", retryAnalyzer = Retry.class)
    public void editProductWithoutChanges() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .selectAmountType(testObject.getLists().get(0).getProducts().get(0).getAmountType())
                .clickAddProduct();

        pages.getProductPage().editSpecifiedItem(testObject.getLists().get(0).getProducts().get(0).getName());

        double itemAmount = pages.getProductPage().getAmountAsDouble();
        double itemCost = pages.getProductPage().getPriceAsDouble();

        pages.getProductPage().clickSaveButtonOnEditItem();

        assertThat(itemAmount, is(10.0));
        assertThat(itemCost, is(3.99));
        assertThat(pages.getProductPage().getCountOfItems(), is(1));
    }

    @Test(description = "Change parameters while edit item", retryAnalyzer = Retry.class)
    public void editProduct() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .selectAmountType(testObject.getLists().get(0).getProducts().get(0).getAmountType())
                .clickAddProduct();

        pages.getProductPage()
                .editSpecifiedItem(testObject.getLists().get(0).getProducts().get(0).getName())
                .setAmount(testObject.getLists().get(0).getProducts().get(1).getAmount())
                .clickSaveButtonOnEditItem();

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage()
                .getSpecifiedItemCost(testObject.getLists().get(0).getProducts().get(0).getName()),
                is(testObject.getLists().get(0).getProducts().get(0).getPrice()));
        assertThat(pages.getProductPage()
                .getSpecifiedItemAmount(testObject.getLists().get(0).getProducts().get(0).getName()),
                is(testObject.getLists().get(0).getProducts().get(1).getPrice()));
    }

    @Test(description = "Change parameters on edit page and click Back button", retryAnalyzer = Retry.class)
    public void discardChangesOnProduct() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .selectAmountType(testObject.getLists().get(0).getProducts().get(0).getAmountType())
                .clickAddProduct();

        pages.getProductPage()
                .editSpecifiedItem(testObject.getLists().get(0).getProducts().get(0).getName())
                .setAmount(testObject.getLists().get(0).getProducts().get(1).getAmount())
                .clickBackButton();

        String dialogTitle = pages.getDialogHelper().getDialogTitle();
        String dialogMessage = pages.getDialogHelper().getDialogMessage();

        pages.getDialogHelper().clickCancel();
        pages.getMainPage().clickOnShoppingListByIndex(0);

        assertThat(dialogTitle, is(DIALOG_SAVE_TITLE));
        assertThat(dialogMessage, is(DIALOG_SAVE_MESSAGE));
        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage()
                .getSpecifiedItemCost(testObject.getLists().get(0).getProducts().get(0).getName()),
                is(testObject.getLists().get(0).getProducts().get(0).getPrice()));
        assertThat(pages.getProductPage()
                .getSpecifiedItemAmount(testObject.getLists().get(0).getProducts().get(0).getName()),
                is(testObject.getLists().get(0).getProducts().get(0).getAmount()));
    }

    @Test(description = "Set long name for product", retryAnalyzer = Retry.class)
    public void setLongNameForItem() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(1).getProducts().get(1).getName())
                .setPrice(testObject.getLists().get(1).getProducts().get(1).getPrice())
                .setAmount(testObject.getLists().get(1).getProducts().get(1).getAmount())
                .selectAmountType(testObject.getLists().get(1).getProducts().get(1).getAmountType())
                .clickAddProduct();

        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getNameOfFirstProduct().length(),
                lessThan(testObject.getLists().get(1).getProducts().get(1).getName().length()));
    }

    @Test(description = "Copy product from one list to another", retryAnalyzer = Retry.class)
    public void copyProductFromOneListToAnother() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .selectAmountType(testObject.getLists().get(0).getProducts().get(0).getAmountType())
                .clickAddProduct();

        pages.getProductPage().clickBackButton();
        pages.getMainPage().addNewBuyList(testObject.getLists().get(1).getShoppingList());
        pages.getProductPage().clickBackButton();
        pages.getMainPage().clickOnSpecifiedShoppingList(testObject.getLists().get(0).getShoppingList());

        pages.getProductPage().clickCopyItem(testObject.getLists().get(0).getProducts().get(0).getName());
        pages.getDialogHelper().selectShoppingList(testObject.getLists().get(1).getShoppingList());

        int itemsCount = pages.getProductPage().getCountOfItems();

        pages.getProductPage().clickBackButton();

        int itemsCount2 = pages.getMainPage().getCountOfShoppingLists();

        pages.getMainPage().clickOnSpecifiedShoppingList(testObject.getLists().get(1).getShoppingList());

        assertThat(itemsCount, is(1));
        assertThat(itemsCount2, is(2));
        assertThat(pages.getProductPage().getCountOfItems(), is(1));
        assertThat(pages.getProductPage().getTotal(),
                is(testObject.getLists().get(0).getProducts().get(0).getPrice() *
                        testObject.getLists().get(0).getProducts().get(0).getAmount()));
    }

    @Test(description = "Copy product when exist only one Shopping List", retryAnalyzer = Retry.class)
    public void copyProductWithOneShoppingList() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .clickAddProduct()
                .clickCopyItem(testObject.getLists().get(0).getProducts().get(0).getName());

        int itemsCount = pages.getDialogHelper().getCountOfShoppingListsInDialog();

        pages.getDialogHelper().selectShoppingList(testObject.getLists().get(0).getShoppingList());

        assertThat(itemsCount, is(1));
        assertThat(pages.getProductPage().getCountOfItems(), is(1));
    }

    @Test(description = "Edit shopping list", retryAnalyzer = Retry.class)
    public void editShoppingList() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .clickAddProduct()
                .clickBackButton();

        pages.getMainPage().editShoppingListByIndex(0);
        pages.getDialogHelper().setEditShoppingListTB(" plus");

        assertThat(pages.getMainPage().getShoppingListName(),
                is(testObject.getLists().get(0).getShoppingList() + " plus"));
    }

    @Test(description = "Remove empty shopping list", retryAnalyzer = Retry.class)
    public void removeEmptyShoppingList() {
        pages.getProductPage().clickBackButton();

        int listsCount = pages.getMainPage().getCountOfShoppingLists();

        pages.getMainPage().removeShoppingListByIndex(0);
        pages.getDialogHelper().clickOk();

        assertThat(listsCount, is(1));
        assertThat(pages.getDialogHelper().getDialogTitle(), is(DIALOG_DELETE_TITLE));
        assertThat(pages.getDialogHelper().getDialogMessage(), is(DIALOG_DELETE_MESSAGE));
        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(0));
    }

    @Test(description = "Remove list with products", retryAnalyzer = Retry.class)
    public void removeShoppingListWithProducts() {
        pages.getProductPage()
                .setProductName(testObject.getLists().get(0).getProducts().get(0).getName())
                .setAmount(testObject.getLists().get(0).getProducts().get(0).getAmount())
                .setPrice(testObject.getLists().get(0).getProducts().get(0).getPrice())
                .selectAmountType(testObject.getLists().get(0).getProducts().get(0).getAmountType())
                .clickAddProduct();

        pages.getProductPage().clickBackButton();

        int itemsCount = pages.getMainPage().getCountOfShoppingLists();

        pages.getMainPage().removeShoppingListByIndex(0);
        pages.getDialogHelper().clickOk();

        assertThat(itemsCount, is(1));
        assertThat(pages.getMainPage().getCountOfShoppingLists(), is(0));
    }
}
