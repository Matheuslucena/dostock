package br.com.mlm.dostock.controller;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.repositories.ProductBatchRepository;
import br.com.mlm.dostock.repositories.ProductRepository;
import br.com.mlm.dostock.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductBatchRepository productBatchRepository;

    @Autowired
    MockMvc mockMvc;

    List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Product p1 = new Product();
        p1.setQuantity(10L);
        p1.setName("Product 01");
        p1.setCode("3333");
        products.add(p1);
    }

    @AfterEach
    void tearDown(){
        reset(productService);
    }

    @Test
    void index() throws Exception {

        given(productService.list(anyInt(), anyInt(), anyString(), anyString())).willReturn(products);

        mockMvc.perform(get("/api/v1/product/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("Product 01")));
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void stockInput() {
    }

    @Test
    void stockOutput() {
    }
}