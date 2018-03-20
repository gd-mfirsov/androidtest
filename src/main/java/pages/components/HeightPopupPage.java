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

public class HeightPopupPage {

    private WebDriverWait wait;

    @AndroidFindBy(id = "com.livestrong.tracker:id/set_height_spinner_options")
    private WebElement heightTypeSpinner;
    @AndroidFindBy(className = "android.widget.ListView")
    private WebElement listView;
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> menu_options;
    @AndroidFindBy(id = "com.livestrong.tracker:id/set_height_centimeters")
    private WebElement heightField;
    @AndroidFindBy(id = "com.livestrong.tracker:id/save_text")
    private WebElement saveButton;

    public HeightPopupPage(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 5);
    }

    @Step
    public void selectOption(String option) {
        wait.until(ExpectedConditions.visibilityOf(heightTypeSpinner));
        heightTypeSpinner.click();
        wait.until(ExpectedConditions.visibilityOf(listView));
        for (WebElement menu_item: menu_options) {
            if (menu_item.getText().equals(option)) {
                menu_item.click();
            }
        }
    }

    @Step
    public void setHeight(int height) {
        wait.until(ExpectedConditions.visibilityOf(heightField));
        heightField.clear();
        heightField.click();
        heightField.sendKeys(Integer.toString(height));
    }

    @Step
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        saveButton.click();
    }
}
