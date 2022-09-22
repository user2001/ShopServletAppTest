package com.example.shopservletapptest.service;


import com.example.shopservletapptest.entity.Shop;
import com.example.shopservletapptest.excption.ShopNotFoundException;
import com.example.shopservletapptest.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop addShop(Shop theShop) {
        shopRepository.save(theShop);
        return theShop;
    }

    @Override
    public String deleteShop(Long shopId) {
        isFound(shopId);
        shopRepository.deleteById(shopId);
        return "Shop with id: " + shopId + " was deleted";
    }

    @Override
    public Optional<Shop> getShop(Long shopId) {
        return shopRepository.findById(shopId);
    }

    @Override
    public Shop updateShop(Shop shop, Long shopId) {
        isFound(shopId);
        var temp = shopRepository.findById(shopId).orElse(null);
        temp.setCity(shop.getCity());
        temp.setShopName(shop.getShopName());
        temp.setStreet(shop.getStreet());
        temp.setCountOfWorkers(shop.getCountOfWorkers());
        temp.setWebsite(shop.isWebsite());
        shopRepository.save(temp);
        return temp;
    }

    public void isFound(Long shopId) {
        boolean isExist = shopRepository.existsById(shopId);
        if (!isExist)
            throw new ShopNotFoundException(
                    "Shop with id: " + shopId + " not found, try to put correct id");
    }
}
