package com.example.shopservletapptest;

import com.example.shopservletapptest.entity.Shop;
import com.example.shopservletapptest.excption.ShopNotFoundException;
import com.example.shopservletapptest.repository.ShopRepository;
import com.example.shopservletapptest.service.ShopServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShopServiceTest {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopServiceImpl shopService;


    @Test
    public void addShopTest() {
        Shop theShop = new Shop("Odessa", "Chuprynky", "Arkadia", 1000, true);
        when(shopRepository.save(any(Shop.class))).thenReturn(theShop);

        Shop savedShop = shopService.addShop(theShop);
        assertThat(savedShop.getShopName()).isSameAs(theShop.getShopName());
    }

    @Test
    public void getShopsTest() {
        Shop shop1 = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        Shop shop2 = new Shop("Lviv", "Lukasha", "Politech", 5000, true);
        Shop shop3 = new Shop("Stryj", "Drohobycka", "Kavoman", 20, false);
        List<Shop> shopList = new ArrayList<>(Arrays.asList(shop1, shop2, shop3));

        when(shopRepository.findAll()).thenReturn(shopList);

        List<Shop> list = shopService.getShops();
        Assertions.assertEquals(3, list.size());
        verify(shopRepository, times(1)).findAll();
    }

    @Test
    public void getShopTest() {
        Shop shop = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        shop.setId(1L);
        when(shopRepository.existsById(shop.getId())).thenReturn(true);
        when(shopRepository.findById(1L)).thenReturn(
                Optional.of(shop));

        Shop theShop = shopService.getShop(1L);
        assertEquals("Dolyna", theShop.getCity());
        assertEquals("Prospekt Svobody", theShop.getStreet());
        assertEquals("Dynastia", theShop.getShopName());
        assertEquals(50, theShop.getCountOfWorkers());
        assertEquals(true, theShop.isWebsite());
    }

    @Test
    public void updateShopTest() {
        Shop shop = new Shop("Dolyna", "Prospekt Svobody",
                "Dynastia", 50, true);
        Long shopId = 5L;
        Shop temp = new Shop();
        when(shopRepository.existsById(shopId)).thenReturn(true);
        when(shopRepository.findById(shopId)).thenReturn(Optional.of(temp));
        temp = shopService.updateShop(shop, shopId);
        assertThat(temp.getShopName()).isSameAs(shop.getShopName());
        verify(shopRepository).save(any(Shop.class));

    }

    @Test
    public void deleteShopTest() {
        Shop shop = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        shop.setId(1L);
        when(shopRepository.existsById(shop.getId())).thenReturn(true);
        shopService.deleteShop(shop.getId());
        verify(shopRepository).deleteById(shop.getId());

    }


    @Test
    void should_throw_exception_when_user_doesnt_exist() {
        Shop shop = new Shop();
        shop.setId(1L);
        assertThrows(ShopNotFoundException.class, () -> shopService.isFound(3L));
        assertThrows(ShopNotFoundException.class, () -> shopService.isFound(null));
        assertThrows(ShopNotFoundException.class, () -> shopService.isFound(-1L));

    }


}
