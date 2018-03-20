package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.components.HeightPopupPage;
import pages.components.WeightPopupPage;
import pages.handlers.NumberPickerHandler;

import java.util.List;

public class UpdateProfilePage {

    private WebDriverWait wait;
    private NumberPickerHandler pickerHandler;
    private HeightPopupPage heightPopupPage;
    private WeightPopupPage weightPopupPage;

    @AndroidFindBy(id = "com.livestrong.tracker:id/spinner_gender")
    private WebElement genderSpinner;
    @AndroidFindBy(id = "com.livestrong.tracker:id/dob_picker")
    private WebElement birthdateDatePicker;
    @AndroidFindBy(id = "com.livestrong.tracker:id/spinner_activity_level")
    private WebElement activeSpinner;
    @AndroidFindBy(id = "com.livestrong.tracker:id/textview_height")
    private WebElement heightTextBox;
    @AndroidFindBy(id = "com.livestrong.tracker:id/textview_weight")
    private WebElement weightTextBox;
    @AndroidFindBy(id = "com.livestrong.tracker:id/textview_weightgoal")
    private WebElement targetWeightTextBox;
    @AndroidFindBy(id = "com.livestrong.tracker:id/next_button")
    private WebElement nextButton;
    @AndroidFindBy(xpath = "//android.widget.TextView")
    private List<WebElement> options;
    @AndroidFindBy(className = "android.widget.TextView")
    private WebElement listVew;

    public UpdateProfilePage(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 5);
        pickerHandler = new NumberPickerHandler(driver);
        heightPopupPage = new HeightPopupPage(driver);
        weightPopupPage = new WeightPopupPage(driver);
    }

    @Step
    public String getGenderText() {
        wait.until(ExpectedConditions.visibilityOf(genderSpinner));
        return genderSpinner.findElement(By.id("android:id/text1")).getText();
    }

    @Step
    public String getBirthdayText() {
        wait.until(ExpectedConditions.visibilityOf(birthdateDatePicker));
        return birthdateDatePicker.getText();
    }

    @Step
    public String getActivityText() {
        wait.until(ExpectedConditions.visibilityOf(activeSpinner));
        return activeSpinner.findElement(By.id("android:id/text1")).getText();
    }

    @Step
    public String getHeightText() {
        wait.until(ExpectedConditions.visibilityOf(heightTextBox));
        return heightTextBox.getText();
    }

    @Step
    public String getWeightText() {
        wait.until(ExpectedConditions.visibilityOf(weightTextBox));
        return weightTextBox.getText();
    }

    @Step
    public String getGoalWeightText() {
        wait.until(ExpectedConditions.visibilityOf(targetWeightTextBox));
        return targetWeightTextBox.getText();
    }

    @Step
    public void clickGender() {
        wait.until(ExpectedConditions.elementToBeClickable(genderSpinner));
        genderSpinner.click();
    }

    @Step
    private void clickBirthday() {
        wait.until(ExpectedConditions.elementToBeClickable(birthdateDatePicker));
        birthdateDatePicker.click();
    }

    @Step
    public void selectOption(String option) {
        wait.until(ExpectedConditions.visibilityOf(listVew));
        for (WebElement menu_option: options) {
            if (menu_option.getText().equals(option)) {
                menu_option.click();
            }
        }
    }

    @Step
    public void clickNext() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
    }

    @Step
    public void setBirthday(String month, int date, int year) {
        this.clickBirthday();
        pickerHandler.setMonth(month);
        pickerHandler.setDate(date);
        pickerHandler.setYear(year);
        pickerHandler.clickOk();
    }

    @Step
    public void setHeight(String measuringType,int height) {
        wait.until(ExpectedConditions.visibilityOf(heightTextBox));
        heightTextBox.click();
        heightPopupPage.selectOption(measuringType);
        heightPopupPage.setHeight(height);
        heightPopupPage.clickSave();
    }

    private void setWeightCommon(String measuringSystem, int weight) {
        weightPopupPage.selectOption(measuringSystem);
        weightPopupPage.setWeight(weight);
        weightPopupPage.clickSave();
    }

    @Step
    public void setWeight(String measuringSystem, int weight) {
        wait.until(ExpectedConditions.visibilityOf(weightTextBox));
        weightTextBox.click();
        this.setWeightCommon(measuringSystem, weight);
    }

    @Step
    public void setGoalWeight(String measuringSystem, int weight) {
        wait.until(ExpectedConditions.visibilityOf(targetWeightTextBox));
        targetWeightTextBox.click();
        this.setWeightCommon(measuringSystem, weight);
    }

    @Step
    public void clickActivity() {
        wait.until(ExpectedConditions.elementToBeClickable(activeSpinner));
        activeSpinner.click();
    }
}
