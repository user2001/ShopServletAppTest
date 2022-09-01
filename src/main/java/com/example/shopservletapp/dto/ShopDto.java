package com.example.shopservletapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@RequiredArgsConstructor
public class ShopDto {

    private String city;
    private String street;
    private String shopName;
    private boolean website;

}
