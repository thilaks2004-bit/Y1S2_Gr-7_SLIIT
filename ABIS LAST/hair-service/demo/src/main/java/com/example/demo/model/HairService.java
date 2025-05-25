package com.example.demo.model;

public class HairService extends Service {
    private String hairType;

    public HairService(String name, int duration, double price, String hairType) {
        super(name, duration, price, "Hair", hairType);
        this.hairType = hairType;
    }

    public String getHairType() {
        return hairType;
    }

    public void setHairType(String hairType) {
        this.hairType = hairType;
    }

    @Override
    public String displayDetails() {
        return "Hair Service: " + getName() + " | Duration: " + getDuration() + " mins | Price: Rs" + getPrice() + " | Hair Type: " + hairType;
    }

    @Override
    public String toString() {
        return super.toString() + "," + hairType;
    }
}