package components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.AbstractPage;

import java.util.List;

public class DialogHelper extends AbstractPage {
    public DialogHelper(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "android:id/alertTitle")
    private WebElement dialogTitle;
    @AndroidFindBy(id = "android:id/message")
    private WebElement dialogMessage;
    @AndroidFindBy(id = "android:id/button1")
    private WebElement dialogOk;
    @AndroidFindBy(id = "android:id/button2")
    private WebElement dialogCancel;
    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.slava.buylist:id/title\"]")
    private List<WebElement> dialogShoppingList;
    @AndroidFindBy(id = "com.slava.buylist:id/listView1")
    private WebElement dialogList;
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@resource-id=\"android:id/customPanel\"]" +
            "//android.widget.EditText")
    private WebElement editShoppingListTB;

    @Step
    public String getDialogTitle() {
        waitForVisibility(dialogTitle);
        return dialogTitle.getText();
    }

    @Step
    public String getDialogMessage() {
        waitForVisibility(dialogMessage);
        return dialogMessage.getText();
    }

    @Step
    public void clickOk() {
        waitForVisibility(dialogOk);
        dialogOk.click();
    }

    @Step
    public void clickCancel() {
        waitForVisibility(dialogCancel);
        dialogCancel.click();
    }

    @Step
    public void selectShoppingList(String listName) {
        waitForVisibility(dialogList);
        dialogShoppingList.stream().filter(s -> s.getText().equals(listName))
                .findFirst().ifPresent(WebElement::click);
    }

    @Step
    public int getCountOfShoppingListsInDialog() {
        waitForVisibility(dialogList);
        return dialogShoppingList.size();
    }

    @Step
    public void setEditShoppingListTB(String shoppingListName) {
        waitForVisibility(editShoppingListTB);
        editShoppingListTB.click();
        editShoppingListTB.sendKeys(shoppingListName);
        dialogOk.click();
    }
}
