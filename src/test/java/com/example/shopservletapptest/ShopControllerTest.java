package com.example.shopservletapptest;

import com.example.shopservletapptest.controller.ShopController;
import com.example.shopservletapptest.entity.Shop;
import com.example.shopservletapptest.service.ShopServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ShopController.class)
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    private ShopServiceImpl shopServiceMock;

    @Test
    void getShopsTest() throws Exception {

        Shop shop1 = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        shop1.setId(1L);

        Shop shop2 = new Shop("Lviv", "Lukasha", "Politech", 5000, true);
        shop2.setId(2L);

        List<Shop> shopList = new ArrayList<>(Arrays.asList(shop1, shop2));

        when(shopServiceMock.getShops()).thenReturn(shopList);

        assertIterableEquals(shopList, shopServiceMock.getShops());

        mockMvc.perform(MockMvcRequestBuilders.get("/shops"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    void addShopTest() throws Exception {

        Shop newShop = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        newShop.setId(1L);
        when(shopServiceMock.addShop(newShop)).thenReturn(newShop);

        MockHttpServletRequestBuilder mockRequest = post("/shops")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newShop));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", Matchers.is("Dolyna")));

    }

    @Test
    void getShopByIdTest() throws Exception {

        Shop shop2 = new Shop("Lviv", "Lukasha", "Politech", 5000, true);
        shop2.setId(2L);

        when(shopServiceMock.getShop(shop2.getId())).thenReturn(Optional.of(shop2));

        mockMvc.perform(MockMvcRequestBuilders.get("/shops/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city", Matchers.is("Lviv")));


    }

    @Test
    void deleteByIdTest() throws Exception {

        Shop newShop = new Shop("Dolyna", "Prospekt Svobody", "Dynastia", 50, true);
        newShop.setId(1L);

        when(shopServiceMock.getShop(newShop.getId())).thenReturn(Optional.of(newShop));

        mockMvc.perform(delete("/shops/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.is("Shop with id: 1 was deleted")));

    }

    @Test
    void updateByIdTest() throws Exception {
        Shop newShop = new Shop();
        newShop.setId(1L);
        Shop updated = new Shop("Lviv", "Stryjska", "Kolosok", 47, true);
        updated.setId(newShop.getId());

        when(shopServiceMock.updateShop(updated, newShop.getId())).thenReturn(updated);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/shops/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapper.writeValueAsString(updated));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

}
