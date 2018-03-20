package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class MainPage extends AbstractPage {
    MainPage(AppiumDriver driver) {
        super(driver);
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
}
