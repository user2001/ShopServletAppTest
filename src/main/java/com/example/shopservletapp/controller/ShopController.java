package com.example.shopservletapp.controller;

import com.example.shopservletapp.dto.ShopDto;
import com.example.shopservletapp.entity.Shop;
import com.example.shopservletapp.excption.ShopErrorResponse;
import com.example.shopservletapp.excption.ShopNotFoundException;
import com.example.shopservletapp.service.ShopServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shops")
public class ShopController {

    private ShopServiceImpl shopService;

    @Autowired
    public ShopController(ShopServiceImpl shopService) {
        this.shopService = shopService;
    }

    @SneakyThrows
    @GetMapping
    public void getShops(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer=response.getWriter();
        shopService.getShops().forEach(e-> writer.println(e.toString()));
        writer.flush();
    }

    @SneakyThrows
    @PostMapping
    public Shop addShop(HttpServletRequest request, HttpServletResponse response) {
        BufferedReader reader = request.getReader();
        String shopJson = reader.lines().collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = objectMapper.readValue(shopJson, Shop.class);
        shopService.addShop(shop);
        return shop;
    }


    @SneakyThrows
    @PostMapping("/dto")
    public ShopDto addShopDTO(HttpServletRequest request, HttpServletResponse response) {
        BufferedReader reader = request.getReader();
        String shopJson = reader.lines().collect(Collectors.joining());

        ObjectMapper objectMapper = new ObjectMapper();
        ShopDto shopDto = objectMapper.readValue(shopJson, ShopDto.class);
        shopService.addShopDTO(shopDto);
        return shopDto;
    }


    @GetMapping( "/{shopId}")
    public Shop getShop(@PathVariable Long shopId) {
        return shopService.getShop(shopId);
    }


    @PutMapping(value = "/{shopId}",
           consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Shop updateShop(@PathVariable Long shopId, @RequestBody Shop shopDto) {
        Shop temp = shopService.updateShop(shopDto, shopId);
        return temp;
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
