package com.example.shopservletapptest.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String street;
    private String shopName;
    private int countOfWorkers;
    private boolean website;

    public Shop(String city, String street, String shopName, int countOfWorkers, boolean website) {
        this.city = city;
        this.street = street;
        this.shopName = shopName;
        this.countOfWorkers = countOfWorkers;
        this.website = website;
    }
}