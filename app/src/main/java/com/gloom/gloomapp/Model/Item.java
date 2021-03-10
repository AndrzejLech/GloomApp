package com.gloom.gloomapp.Model;

public class Item {
    private String uid;
    private String name;
    private String description;
    private String price;
    private String type;
    private String state;


    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }


    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setType(String type) {
        this.type = type;
    }
}


