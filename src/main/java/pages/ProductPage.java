package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage extends AbstractPage{
    public ProductPage(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.slava.buylist:id/editText1")
    private WebElement productTB;
    @AndroidFindBy(id = "com.slava.buylist:id/textView2")
    private WebElement totalValue;
    @AndroidFindBy(id = "com.slava.buylist:id/button2")
    private WebElement addProductButton;
    @AndroidFindBy(id = "com.slava.buylist:id/spinner1")
    private WebElement amountTypeSpinner;
    @AndroidFindBy(id = "com.slava.buylist:id/spinner2")
    private WebElement categorySpinner;
    @AndroidFindBy(id = "com.slava.buylist:id/editText2")
    private WebElement priceTB;
    @AndroidFindBy(id = "com.slava.buylist:id/editText3")
    private WebElement amountTB;
    @AndroidFindBy(id = "com.slava.buylist:id/editText4")
    private WebElement commentTB;
    @AndroidFindBy(id = "com.slava.buylist:id/item")
    private List<WebElement> itemsList;

    @Step
    public void setProductName(String productName) {
        waitForVisibility(productTB);
        productTB.clear();
        productTB.sendKeys(productName);
    }

    @Step
    public void setPrice(double productPrice) {
        waitForVisibility(priceTB);
        priceTB.clear();
        priceTB.sendKeys(Double.toString(productPrice));
    }

    @Step
    public void setAmount(double productAmount) {
        waitForVisibility(amountTB);
        amountTB.clear();
        amountTB.sendKeys(Double.toString(productAmount));
    }

    @Step
    public void setComment(String comment) {
        waitForVisibility(commentTB);
        commentTB.clear();
        commentTB.sendKeys(comment);
    }

    @Step
    public void selectAmountType(String amountType) {
        amountTypeSpinner.click();
        waitForVisibility(driver
                .findElement(By
                        .xpath("//android.widget.ListView[@resource-id=\"android:id/select_dialog_listview\"]")));
        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())" +
                ".scrollIntoView(new UiSelector().text(\"" + amountType + "\"));")).click();
    }

    @Step
    public void selectCategory(String category) {
        categorySpinner.click();
        waitForVisibility(driver
                .findElement(By
                        .xpath("//android.widget.ListView[@resource-id=\"android:id/select_dialog_listview\"]")));
        driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector())" +
                ".scrollIntoView(new UiSelector().text(\"" + category + "\"));")).click();
    }

    @Step
    public void clickAddProduct() {
        waitForVisibility(addProductButton);
        addProductButton.click();
    }

    private WebElement getSpecifiedItem(String itemName) {
        waitForVisibility(itemsList.get(0));
        return itemsList.stream()
                .filter(i -> i.findElement(By.id("com.slava.buylist:id/title")).getText().equals(itemName))
                .findFirst().get();
    }

    @Step
    public String getSpecifiedItemCommentText(String itemName) {
        waitForVisibility(itemsList.get(0));
        return getSpecifiedItem(itemName).findElement(By.id("com.slava.buylist:id/str1")).getText();
    }

    @Step
    public double getSpecifiedItemCost(String itemName) {
        return Double.parseDouble(getSpecifiedItem(itemName)
                .findElement(By.id("com.slava.buylist:id/textView1")).getText().split(" ")[0]);
    }

    @Step
    public double getSpecifiedItemAmount(String itemName) {
        return Double.parseDouble(getSpecifiedItem(itemName)
                .findElement(By.id("com.slava.buylist:id/TextView01")).getText().split(" ")[0]);
    }

    @Step
    public int getCountOfItems() {
        return itemsList.size();
    }

    private void longPressOnSpecifiedItem(String itemName) {
        TouchAction touchAction = new TouchAction(driver);
        waitForVisibility(itemsList.get(0));
        touchAction.longPress(getSpecifiedItem(itemName)).perform();
    }

    @Step
    public String getTotal() {
        waitForVisibility(totalValue);
        return totalValue.getText();
    }

    @Step
    public void deleteSpecifiedItem(String itemName) {
        longPressOnSpecifiedItem(itemName);
        waitForVisibility(driver.findElement(By.xpath("//android.widget.TextView[@text='Remove']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Remove']")).click();
        waitForVisibility(driver.findElement(MobileBy.xpath("//android.widget.Button[@text='YES']")));
        driver.findElement(By.xpath("//android.widget.Button[@text='YES']")).click();
    }

    @Step
    public void editSpecifiedItem(String itemName) {
        longPressOnSpecifiedItem(itemName);
        waitForVisibility(driver.findElement(By.xpath("//android.widget.TextView[@text='Edit']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Edit']")).click();
    }

    @Step
    public void setSpecifiedItemAsBought(String itemName) {
        getSpecifiedItem(itemName).findElement(By.id("com.slava.buylist:id/imageView1")).click();
    }

    @Step
    public void clickSaveButtonOnEditItem() {
        waitForVisibility(addProductButton);
        addProductButton.click();
    }

    @Step
    public double getPriceAsDouble() {
        waitForVisibility(priceTB);
        return Double.parseDouble(priceTB.getText());
    }

    @Step
    public double getAmountAsDouble() {
        waitForVisibility(amountTB);
        return Double.parseDouble(amountTB.getText());
    }

    @Step
    public void clickCopyItem(String itemName) {
        longPressOnSpecifiedItem(itemName);
        waitForVisibility(driver.findElement(By.xpath("//android.widget.TextView[@text='Copy']")));
        driver.findElement(By.xpath("//android.widget.TextView[@text='Copy']")).click();
    }

    @Step
    public String getNameOfFirstProduct() {
        return itemsList.get(0).findElement(By.id("com.slava.buylist:id/title")).getText();
    }
}
