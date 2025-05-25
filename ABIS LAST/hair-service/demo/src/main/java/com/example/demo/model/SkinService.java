package com.example.demo.model;

public class SkinService extends Service {
    private String skinType;

    public SkinService(String name, int duration, double price, String skinType) {
        super(name, duration, price, "Skin", skinType);
        this.skinType = skinType;
    }

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    @Override
    public String displayDetails() {
        return "Skin Service: " + getName() + " | Duration: " + getDuration() + " mins | Price: Rs" + getPrice() + " | Skin Type: " + skinType;
    }

    @Override
    public String toString() {
        return super.toString() + "," + skinType;
    }
}