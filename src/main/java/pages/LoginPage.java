package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriverWait wait;
    @AndroidFindBy(id = "com.livestrong.tracker:id/facebook_login_button")
    private WebElement fbButton;
    @AndroidFindBy(id = "com.livestrong.tracker:id/log_in_btn")
    private WebElement emailButton;
    @AndroidFindBy(id = "com.livestrong.tracker:id/sign_up_btn")
    private WebElement signUpButton;
    @AndroidFindBy(xpath = "//android.widget.EditText[text()=\"Email address or phone number\"]")
    private WebElement emailField;
    @AndroidFindBy(xpath = "//android.widget.EditText[text()=\"Facebook password\"]")
    private WebElement passwordField;
    @AndroidFindBy(id = "u_0_5")
    private WebElement fbLoginButton;
    @AndroidFindBy(id = "u_0_3")
    private WebElement fbConfirmLoginButton;
    @AndroidFindBy(id = "com.livestrong.tracker:id/log_in_btn")
    private WebElement loginViaEmailButton;

    public LoginPage(AppiumDriver androidDriver) {
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
        wait = new WebDriverWait(androidDriver, 10);
    }

    @Step
    public boolean isFacebookButtonDisplayed() {
        return fbButton.isDisplayed();
    }

    @Step
    public boolean isLoginButtonDisplayed() {
        return emailButton.isDisplayed();
    }

    @Step
    public boolean isSignUpButtonDisplayed() {
        return signUpButton.isDisplayed();
    }

    @Step
    public void clickLoginViaFacebook() {
        wait.until(ExpectedConditions.visibilityOf(fbButton));
        fbButton.click();
    }

    @Step
    public void setFacebookEmailField(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.click();
        emailField.sendKeys(email);
    }

    @Step
    public void setFacebookPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.click();
        passwordField.sendKeys(password);
    }

    @Step
    public void clickLoginButtonOnFacebookPopup() {
        wait.until(ExpectedConditions.visibilityOf(fbLoginButton));
        fbLoginButton.click();
    }

    @Step
    public void clickConfirmLoginViaFacebook() {
        wait.until(ExpectedConditions.visibilityOf(fbConfirmLoginButton));
        fbConfirmLoginButton.click();
    }

    @Step
    public void clickLoginViaEmail() {
        wait.until(ExpectedConditions.visibilityOf(loginViaEmailButton));
        loginViaEmailButton.click();
    }
}
