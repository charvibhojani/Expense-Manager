package com.tooandunitils.expensemanager;

public class showItems {

    String category;
    String price;
    int id;

    public showItems(String category, String price, int id) {
        this.category = category;
        this.price = price;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
