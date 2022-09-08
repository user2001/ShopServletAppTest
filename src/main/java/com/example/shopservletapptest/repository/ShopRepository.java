package com.example.shopservletapptest.repository;

import com.example.shopservletapptest.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
}
