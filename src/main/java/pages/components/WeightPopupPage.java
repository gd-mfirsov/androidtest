package pages.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WeightPopupPage {

    private WebDriverWait wait;

    @AndroidFindBy(id = "com.livestrong.tracker:id/set_weight_spinner_options")
    private WebElement weightSpinner;
    @AndroidFindBy(className = "android.widget.ListView")
    private WebElement listView;
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> menu_options;
    @AndroidFindBy(id = "com.livestrong.tracker:id/set_weight_edittext")
    private WebElement weightField;
    @AndroidFindBy(id = "com.livestrong.tracker:id/save_text")
    private WebElement saveButton;

    public WeightPopupPage(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 5);
    }

    @Step
    public void selectOption(String option) {
        wait.until(ExpectedConditions.visibilityOf(weightSpinner));
        weightSpinner.click();
        wait.until(ExpectedConditions.visibilityOf(listView));
        for (WebElement menu_item: menu_options) {
            if (menu_item.getText().equals(option)) {
                menu_item.click();
            }
        }
    }

    @Step
    public void setWeight(int weight) {
        wait.until(ExpectedConditions.visibilityOf(weightField));
        weightField.clear();
        weightField.click();
        weightField.sendKeys(Integer.toString(weight));
    }

    @Step
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }
}
