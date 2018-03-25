package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

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
        waitForInvisibility(dialogOk);
    }

    @Step
    public void clickCancel() {
        waitForVisibility(dialogCancel);
        dialogCancel.click();
        waitForInvisibility(dialogCancel);
    }
}
