package com.tooandunitils.expensemanager;

public class TransactionItem {

    String category;
    String price;
    String time;
    String type;

    public TransactionItem(String category, String price, String time, String type) {
        this.category = category;
        this.price = price;
        this.time = time;
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
