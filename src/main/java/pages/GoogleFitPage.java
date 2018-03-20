package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

public class GoogleFitPage extends AbstractPage {
    public GoogleFitPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.widget.TextView[@index=\"0\"]")
    private WebElement title;
    @AndroidFindBy(xpath = "//android.widget.TextView[@index=\"1\"]")
    private WebElement description;
    @AndroidFindBy(id = "com.livestrong.tracker:id/google_fit_skip_button")
    private WebElement skipButton;

    @Step
    public String getTitleText() {
        waitForElementVisibility(title);
        return title.getText();
    }

    @Step
    public String getDescText() {
        waitForElementVisibility(description);
        return description.getText();
    }

    @Step
    public void clickSkip() {
        waitForElementToBeClikable(skipButton);
        skipButton.click();
    }
}
