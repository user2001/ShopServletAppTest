package com.example.shopservletapptest;


import com.example.shopservletapptest.entity.Shop;
import com.example.shopservletapptest.repository.ShopRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        List<Shop> shopList = Arrays.asList(
                new Shop("Lviv", "Kozelnytska", "UCU", 300, true));
        shopRepository.saveAll(shopList);
        System.out.println(shopList);
    }

    @AfterEach
    public void destroyAll() {
        shopRepository.deleteAll();
    }

    @Test
    public void addShopTest() {
        Shop shop = new Shop("Kyiv", "Shevchenka", "Blyzenko", 500, true);
        shop = entityManager.persistAndFlush(shop);
        assertThat(shopRepository.findById(shop.getId()).get()).isEqualTo(shop);
        System.out.println(shop);
    }

    @Test
    public void findAllTest(){
        List<Shop> shopList=shopRepository.findAll();
        assertThat(shopList.size()).isGreaterThanOrEqualTo(1);
    }

}
