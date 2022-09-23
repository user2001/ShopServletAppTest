package com.example.shopservletapptest;

import com.example.shopservletapptest.entity.Shop;
public class TestShop {
    static Shop shopForTest() {
        Shop shop = new Shop("Dolyna", "Prospekt Svobody",
                "Dynastia", 50, true);
        shop.setId(1L);
        return shop;

    }
}

