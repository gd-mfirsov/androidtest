package pages;

import core.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;

public class AbstractPage {

    private WebDriverWait wait;
    protected AndroidDriver androidDriver;

    protected AbstractPage() {
        androidDriver = new DriverManager().getAndroidDriver();
        wait = new WebDriverWait(new DriverManager().getAndroidDriver(), 10);
        PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void clickBackButton() {
        (new DriverManager().getAndroidDriver()).pressKeyCode(AndroidKeyCode.BACK);
    }
}
