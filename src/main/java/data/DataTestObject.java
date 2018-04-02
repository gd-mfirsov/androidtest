package data;

/**
 * Created by JacksonGenerator on 4/2/18.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class DataTestObject {
    @JsonProperty("lists")
    private List<ListsItem> lists;

    public List<ListsItem> getLists() {
        return lists;
    }
}