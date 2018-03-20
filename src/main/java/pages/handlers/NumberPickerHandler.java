package pages.handlers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NumberPickerHandler {

    private WebDriverWait wait;

    @AndroidFindBy(xpath = "//android.widget.NumberPicker[@index=\"0\"]")
    private WebElement monthPicker;
    @AndroidFindBy(xpath = "//android.widget.NumberPicker[@index=\"1\"]")
    private WebElement datePicker;
    @AndroidFindBy(xpath = "//android.widget.NumberPicker[@index=\"2\"]")
    private WebElement yearPicker;
    @AndroidFindBy(id = "android:id/button1")
    private WebElement okButton;

    public NumberPickerHandler(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 5);
    }

    @Step
    public void setMonth(String month) {
        wait.until(ExpectedConditions.visibilityOf(monthPicker));
        monthPicker.click();
        monthPicker.sendKeys(month);
    }

    @Step
    public void setDate(int date) {
        wait.until(ExpectedConditions.visibilityOf(datePicker));
        datePicker.click();
        datePicker.sendKeys(Integer.toString(date));
    }

    @Step
    public void setYear(int year) {
        wait.until(ExpectedConditions.visibilityOf(yearPicker));
        yearPicker.click();
        yearPicker.sendKeys(Integer.toString(year));
    }

    @Step
    public void clickOk() {
        wait.until(ExpectedConditions.elementToBeClickable(okButton));
        okButton.click();
    }
}
