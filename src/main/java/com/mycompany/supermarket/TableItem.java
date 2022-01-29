package com.mycompany.supermarket;

public class TableItem {
    private String srno;
    private String itemName;
    private float price;
    private int quantity;

    public TableItem(String srno, String itemName, float price, int quantity) {
        this.srno = srno;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
