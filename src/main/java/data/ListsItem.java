package data;

/**
 * Created by JacksonGenerator on 4/2/18.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class ListsItem {
    @JsonProperty("shoppingList")
    private String shoppingList;
    @JsonProperty("products")
    private List<ProductsItem> products;

    public String getShoppingList() {
        return shoppingList;
    }

    public List<ProductsItem> getProducts() {
        return products;
    }

    public void setShoppingList(String shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void setProducts(List<ProductsItem> products) {
        this.products = products;
    }
}