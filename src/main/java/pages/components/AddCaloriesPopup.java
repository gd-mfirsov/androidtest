package pages.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.AbstractPage;

public class AddCaloriesPopup extends AbstractPage {
    AddCaloriesPopup(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_breakfast")
    private WebElement breakfast;
    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_lunch")
    private WebElement lunch;
    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_dinner")
    private WebElement dinner;
    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_snack")
    private WebElement snack;
    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_exercise")
    private WebElement exercise;
    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_water")
    private WebElement water;
    @AndroidFindBy(id = "com.livestrong.tracker:id/btn_weight")
    private WebElement weight;

    @Step
    public void clickBreakfast() {
        waitForElementToBeClikable(breakfast);
        breakfast.click();
    }

    @Step
    public void clickLunch() {
        waitForElementToBeClikable(lunch);
        lunch.click();
    }

    @Step
    public void clickDinner() {
        waitForElementToBeClikable(dinner);
        dinner.click();
    }

    @Step
    public void clickSnack() {
        waitForElementToBeClikable(snack);
        snack.click();
    }

    @Step
    public void clickExercise() {
        waitForElementToBeClikable(exercise);
        exercise.click();
    }

    @Step
    public void clickWater() {
        waitForElementToBeClikable(water);
        water.click();
    }

    @Step
    public void clickWeight() {
        waitForElementToBeClikable(weight);
        weight.click();
    }

    @Step
    public String getCurrentWeight() {
        waitForElementVisibility(weight.findElement(By.id("com.livestrong.tracker:id/weight")));
        return weight.findElement(By.id("com.livestrong.tracker:id/weight")).getText();
    }

}
