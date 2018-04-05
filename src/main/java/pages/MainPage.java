package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MainPage extends AbstractPage{
    public MainPage() {
        super();
    }

    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private WebElement addNew;
    @AndroidFindBy(id = "com.slava.buylist:id/editText1")
    private WebElement textBox;
    @AndroidFindBy(xpath = "//android.widget.ListView//android.widget.RelativeLayout")
    private List<WebElement> shoppingLists;

    @Step
    public MainPage addNewBuyList(String buyListName) {
        waitForVisibility(textBox);
        textBox.click();
        textBox.sendKeys(buyListName);
        addNew.click();
        return this;
    }

    @Step
    public MainPage clickOnSpecifiedShoppingList(String shoppingListName) {
        shoppingLists.stream().filter(sh -> sh.findElement(By.id("com.slava.buylist:id/title"))
                .getText().equals(shoppingListName)).findFirst().ifPresent(WebElement::click);
        return this;
    }

    @Step
    public MainPage clickOnShoppingListByIndex(int index) {
        shoppingLists.get(index).click();
        return this;
    }

    @Step
    public MainPage removeShoppingListByIndex(int index) {
        shoppingLists.get(index).findElement(By.id("com.slava.buylist:id/imageView1")).click();
        return this;
    }

    @Step
    public MainPage editShoppingListByIndex(int index) {
        shoppingLists.get(index).findElement(By.id("com.slava.buylist:id/imageView2")).click();
        return this;
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
