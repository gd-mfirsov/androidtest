package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelectCaloriePage extends AbstractPage {
    public SelectCaloriePage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.livestrong.tracker:id/card_view_calorie_goal")
    private List<WebElement> calorieCards;

    @Step
    public int getCountOfCalorieCards() {
        return calorieCards.size();
    }

    @Step
    public void clickOnSpecifiedCard(String cardName) {
        calorieCards.stream()
                .filter(c -> c.findElement(By.id("com.livestrong.tracker:id/title")).getText().equals(cardName))
                .findFirst().ifPresent(WebElement::click);
    }
}
