package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import pages.components.AddCaloriesPopup;

public class MainPage extends AbstractPage {
    public MainPage(AppiumDriver driver) {
        super(driver);
        addCaloriesPopup = new AddCaloriesPopup(driver);
    }

    @AndroidFindBy(id = "com.livestrong.tracker:id/fab")
    private WebElement addButton;
    @AndroidFindBy(id = "com.livestrong.tracker:id/calorie_remaining")
    private WebElement caloriesLeft;
    @AndroidFindBy(id = "com.livestrong.tracker:id/tvConsumed")
    private WebElement consumedCalorie;
    @AndroidFindBy(id = "com.livestrong.tracker:id/tvBurned")
    private WebElement burnedCalorie;
    @AndroidFindBy(id = "com.livestrong.tracker:id/tvNet")
    private WebElement netCalorie;
    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]")
    private WebElement menuButton;

    public AddCaloriesPopup addCaloriesPopup;

    @Step
    public String getCaloriesLeftText() {
        waitForElementVisibility(caloriesLeft);
        return caloriesLeft.getText();
    }

    @Step
    public int getLeftCaloriesAsInt() {
        waitForElementVisibility(caloriesLeft);
        String dummy = caloriesLeft.getText().split(" ")[0];
        return Integer.parseInt(dummy);
    }

    @Step
    public int getConsumedCaloriesText() {
        waitForElementVisibility(consumedCalorie);
        return Integer.parseInt(consumedCalorie.getText());
    }

    @Step
    public int getBurnedCalorieText() {
        waitForElementVisibility(burnedCalorie);
        return Integer.parseInt(burnedCalorie.getText());
    }

    @Step
    public int getNetCalorieText() {
        waitForElementVisibility(netCalorie);
        return Integer.parseInt(netCalorie.getText());
    }

    @Step
    public void clickAddButton() {
        waitForElementToBeClikable(addButton);
        addButton.click();
    }

    @Step
    public void clickMenuButton() {
        waitForElementToBeClikable(menuButton);
        menuButton.click();
    }
}
