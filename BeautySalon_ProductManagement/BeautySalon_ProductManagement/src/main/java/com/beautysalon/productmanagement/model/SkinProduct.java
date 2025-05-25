// model/SkinProduct.java
package com.beautysalon.productmanagement.model;

public class SkinProduct extends Product {
    public SkinProduct(String id, String name, double price, int stock, String imageUrl, String description) {
        super(id, name, "Skin", price, stock, imageUrl, description);
    }
}

