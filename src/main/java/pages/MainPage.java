package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends AbstractPage{
    public MainPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private WebElement addNew;
    @AndroidFindBy(id = "com.slava.buylist:id/editText1")
    private WebElement textBox;
    @AndroidFindBy(xpath = "//android.widget.ListView//android.widget.RelativeLayout")
    private List<WebElement> shoppingLists;

    @Step
    public void addNewBuyList(String buyListName) {
        waitForVisibility(textBox);
        textBox.click();
        textBox.sendKeys(buyListName);
        addNew.click();
    }

    @Step
    public void clickOnSpecifiedShoppingList(String shoppingListName) {
        shoppingLists.stream().filter(sh -> sh.findElement(By.id("com.slava.buylist:id/title"))
                .getText().equals(shoppingListName)).findFirst().ifPresent(WebElement::click);
    }

    @Step
    public void clickOnShoppingListByIndex(int index) {
        shoppingLists.get(index).click();
    }

    @Step
    public void removeShoppingListByIndex(int index) {
        shoppingLists.get(index).findElement(By.id("com.slava.buylist:id/imageView1")).click();
    }

    @Step
    public void editShoppingListByIndex(int index) {
        shoppingLists.get(index).findElement(By.id("com.slava.buylist:id/imageView2")).click();
    }

    @Step
    public String getShoppingListName() {
        return shoppingLists.get(0).findElement(By.id("com.slava.buylist:id/title")).getText();
    }

    @Step
    public int getCountOfShoppingLists() {
        return shoppingLists.size();
    }
}
