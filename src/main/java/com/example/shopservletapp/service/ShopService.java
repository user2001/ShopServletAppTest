package com.example.shopservletapp.service;

import com.example.shopservletapp.entity.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> getShops();

    Shop addShop(Shop theShop);

    String deleteShop(Long shopId);

    Shop getShop(Long shopId);

    Shop updateShop(Shop shop, Long shopId);
}
