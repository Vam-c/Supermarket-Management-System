package com.mycompany.supermarket;

public class Item {
    private String srno;
    private String name;
    private float price;

    public Item(String srno, String name, float price) {
        this.srno = srno;
        this.name = name;
        this.price = price;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
