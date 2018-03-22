package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TrackFoodPage extends AbstractPage {
    public TrackFoodPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.support.v7.app.ActionBar$Tab[@index=\"1\"]")
    private WebElement myFoodTab;
    @AndroidFindBy(id = "com.livestrong.tracker:id/quick_add_button")
    private WebElement addQuickCalories;
    @AndroidFindBy(id = "com.livestrong.tracker:id/edit_text_calories")
    private WebElement caloriesField;
    @AndroidFindBy(id = "com.livestrong.tracker:id/spinnerMeal")
    private WebElement mealDropdown;
    @AndroidFindBy(className = "android.widget.ListView")
    private WebElement listView;
    @AndroidFindBy(className = "android.widget.TextView")
    private List<WebElement> mealList;
    @AndroidFindBy(id = "com.livestrong.tracker:id/action_create_food")
    private WebElement doneButton;

    @Step
    public void clickMyFoodTab() {
        waitForElementToBeClikable(myFoodTab);
        myFoodTab.click();
    }

    @Step
    public void clickQuickAddCalories() {
        waitForElementToBeClikable(addQuickCalories);
        addQuickCalories.click();
    }

    @Step
    public void setCalories(int calories) {
        waitForElementVisibility(caloriesField);
        caloriesField.click();
        caloriesField.clear();
        caloriesField.sendKeys(Integer.toString(calories));
    }

    @Step
    public void selectMeal(String meal) {
        waitForElementToBeClikable(mealDropdown);
        mealDropdown.click();
        waitForElementVisibility(listView);
        mealList.stream().filter(m -> m.getText().equals(meal)).findFirst().ifPresent(WebElement::click);
    }

    @Step
    public void clickDone() {
        waitForElementToBeClikable(doneButton);
        doneButton.click();
    }
}
