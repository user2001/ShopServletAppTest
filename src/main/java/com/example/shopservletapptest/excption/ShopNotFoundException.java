package com.example.shopservletapptest.excption;

public class ShopNotFoundException extends  RuntimeException{
    public ShopNotFoundException(String message) {
        super(message);
    }
}
