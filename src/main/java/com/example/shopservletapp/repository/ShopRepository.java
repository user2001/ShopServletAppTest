package com.example.shopservletapp.repository;

import com.example.shopservletapp.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
}
