package pages;

import components.DialogHelper;
import pages.MainPage;
import pages.ProductPage;

public class Pages {
    private MainPage mainPage;
    private ProductPage productPage;
    private DialogHelper dialogHelper;

    public MainPage getMainPage() {
        if (mainPage == null) {
            mainPage = new MainPage();
        }
        return mainPage;
    }

    public ProductPage getProductPage() {
        if (productPage == null) {
            productPage = new ProductPage();
        }
        return productPage;
    }

    public DialogHelper getDialogHelper() {
        if (dialogHelper == null) {
            dialogHelper = new DialogHelper();
        }
        return dialogHelper;
    }
}
