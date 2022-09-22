package com.example.shopservletapptest.service;

import com.example.shopservletapptest.entity.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopService {
    List<Shop> getShops();

    Shop addShop(Shop theShop);

    String deleteShop(Long shopId);

   Optional< Shop> getShop(Long shopId);

    Shop updateShop(Shop shop, Long shopId);
}
