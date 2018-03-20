package pages.helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginHelper {

    private WebDriverWait wait;

    @AndroidFindBy(id = "com.livestrong.tracker:id/user_name_input")
    private WebElement emailField;
    @AndroidFindBy(id = "com.livestrong.tracker:id/password_input")
    private WebElement passwordField;
    @AndroidFindBy(id = "com.livestrong.tracker:id/action_login")
    private WebElement completeButton;


    public LoginHelper(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        wait = new WebDriverWait(driver, 5);
    }

    @Step
    public void performLogin(String email, String pass) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.click();
        emailField.sendKeys(email);
        passwordField.click();
        passwordField.sendKeys(pass);
        completeButton.click();
    }
}
