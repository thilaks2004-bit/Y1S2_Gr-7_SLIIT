// model/HairProduct.java
package com.beautysalon.productmanagement.model;

public class HairProduct extends Product {
    public HairProduct(String id, String name, double price, int stock, String imageUrl, String description) {
        super(id, name, "Hair", price, stock, imageUrl, description);
    }
}

