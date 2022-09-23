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

import static com.example.shopservletapptest.TestShop.shopForTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        when(shopRepository.save(any(Shop.class))).thenReturn(shopForTest());

        Shop actualShop = shopService.addShop(shopForTest());
        assertThat(actualShop.getShopName()).isSameAs(shopForTest().getShopName());
    }

    @Test
    public void getShopsTest() {
        Shop shop1 = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        Shop shop2 = new Shop("Lviv", "Lukasha", "Politech", 5000, true);
        Shop shop3 = new Shop("Stryj", "Drohobycka", "Kavoman", 20, false);
        List<Shop> expectedList = new ArrayList<>(Arrays.asList(shop1, shop2, shop3));

        when(shopRepository.findAll()).thenReturn(expectedList);

        List<Shop> actualList = shopService.getShops();
        Assertions.assertEquals(3, actualList.size());
        verify(shopRepository, times(1)).findAll();
    }

    @Test
    public void getShopTest() {

        when(shopRepository.findById(1L)).thenReturn(Optional.of(shopForTest()));

        Optional<Shop> actualShop = shopService.getShop(1L);
        Assertions.assertEquals(shopForTest().getCity(), actualShop.get().getCity());
        Assertions.assertEquals(shopForTest().getStreet(), actualShop.get().getStreet());
        Assertions.assertEquals(shopForTest().getShopName(), actualShop.get().getShopName());
        Assertions.assertEquals(shopForTest().getCountOfWorkers(), actualShop.get().getCountOfWorkers());
        Assertions.assertTrue(actualShop.get().isWebsite());
    }

    @Test
    public void updateShopTest() {

        Long shopId = 5L;

        Shop expectedShop = new Shop();

        when(shopRepository.existsById(shopId)).thenReturn(true);
        when(shopRepository.findById(shopId)).thenReturn(Optional.of(expectedShop));

        expectedShop = shopService.updateShop(shopForTest(), shopId);

        assertThat(expectedShop.getShopName()).isSameAs(shopForTest().getShopName());
        verify(shopRepository).save(any(Shop.class));

    }

    @Test
    public void deleteShopTest() {

        when(shopRepository.existsById(shopForTest().getId())).thenReturn(true);

        shopService.deleteShop(shopForTest().getId());
        verify(shopRepository).deleteById(shopForTest().getId());

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
