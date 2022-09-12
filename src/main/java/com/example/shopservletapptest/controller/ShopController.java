package com.example.shopservletapptest.controller;

import com.example.shopservletapptest.entity.Shop;
import com.example.shopservletapptest.excption.ShopErrorResponse;
import com.example.shopservletapptest.excption.ShopNotFoundException;
import com.example.shopservletapptest.service.ShopServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopServiceImpl shopService;


    //1. створити методи POST, GET  які приймають httpServletRequest та httpServletResponse
    // та серіалізувати їх за допомогою Object Mapper
    @SneakyThrows
    @GetMapping
    public void getShops(HttpServletResponse response) {
        PrintWriter writer = response.getWriter();
        shopService.getShops().forEach(e -> writer.println(e.toString()));
        writer.flush();
    }

    @SneakyThrows
    @PostMapping
    public Shop addShop(HttpServletRequest request) {
        BufferedReader reader = request.getReader();
        String shopJson = reader.lines().collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = objectMapper.readValue(shopJson, Shop.class);
        shopService.addShop(shop);
        return shop;
    }

    @GetMapping("/{shopId}")
    public Shop getShop(@PathVariable Long shopId) {
        return shopService.getShop(shopId);
    }


    @PutMapping(value = "/{shopId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Shop updateShop(@PathVariable Long shopId, @RequestBody Shop shop) {
        Shop updated=shopService.updateShop(shop, shopId);
        return updated;
    }

    @DeleteMapping("/{shopId}")
    public String deleteShops(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
        return "Shop with id: " + shopId + " was deleted";
    }

    @ExceptionHandler
    public ResponseEntity<ShopErrorResponse> handleException(ShopNotFoundException exc) {
        ShopErrorResponse error = new ShopErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
