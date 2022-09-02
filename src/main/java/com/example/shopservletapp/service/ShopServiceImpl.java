package com.example.shopservletapp.service;


import com.example.shopservletapp.dto.ShopDto;
import com.example.shopservletapp.entity.Shop;
import com.example.shopservletapp.excption.ShopNotFoundException;
import com.example.shopservletapp.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @PostConstruct
    void init() {
        Shop shop1 = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        Shop shop2 = new Shop("Lviv", "Lukasha", "Politech", 5000, true);
        Shop shop3 = new Shop("Stryj", "Drohobycka", "Kavoman", 20, false);
        Shop shop4 = new Shop("Kyiv", "Prospekt Nezaleznosti", "Svoboda", 47, false);
        Shop shop5 = new Shop("Odessa", "Naberezna", "Arkadia", 700, true);
        shopRepository.saveAll(Arrays.asList(shop1, shop5, shop2, shop4, shop3));
    }

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public Shop addShop(Shop theShop) {
        shopRepository.save(theShop);
        System.out.println("shop added");
        return theShop;
    }

    public ShopDto addShopDTO(ShopDto shopDto) {
        Shop shop = convertToEntity(shopDto);
        shopRepository.save(shop);
        return shopDto;
    }

    private Shop convertToEntity(ShopDto shopDto) {
        Shop shop = new Shop();
        shop.setShopName(shopDto.getShopName());
        shop.setCity(shopDto.getCity());
        shop.setStreet(shopDto.getStreet());
        shop.setWebsite(shopDto.isWebsite());
        return shop;
    }

    @Override
    public String deleteShop(Long shopId) {
        isFound(shopId);
        shopRepository.deleteById(shopId);
        return "Shop with id: " + shopId + " was deleted";
    }

    @Override
    public Shop getShop(Long shopId) {
        isFound(shopId);
        Shop shop = shopRepository.findById(shopId).orElse(null);
        return shop;
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
