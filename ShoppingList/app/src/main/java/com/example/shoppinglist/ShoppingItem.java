package com.example.shoppinglist;

public class ShoppingItem {

    int shoppingListID;
    int category;
    String name;
    String description;
    String price;
    boolean purchased;

    public ShoppingItem(int category, String name, String description, String price, boolean purchased) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.purchased = purchased;
    }

    public int getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(int shoppingListID) {
        this.shoppingListID = shoppingListID;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
