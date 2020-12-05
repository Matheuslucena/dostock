package br.com.mlm.dostock.controller;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.domain.ProductBatch;
import br.com.mlm.dostock.dto.InventoryDTO;
import br.com.mlm.dostock.dto.ProductDTO;
import br.com.mlm.dostock.dto.mapper.ProductMapper;
import br.com.mlm.dostock.repositories.ProductBatchRepository;
import br.com.mlm.dostock.repositories.ProductRepository;
import br.com.mlm.dostock.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductBatchRepository productBatchRepository;

    @MockBean
    ProductMapper productMapper;

    @Autowired
    MockMvc mockMvc;

    List<Product> products = new ArrayList<>();

    ProductDTO productDTO;
    InventoryDTO inventoryDTO;

    @BeforeEach
    void setUp() {
        Product p1 = new Product();
        p1.setQuantity(10L);
        p1.setName("Product 01");
        p1.setCode("3333");
        products.add(p1);

        productDTO = new ProductDTO();
        productDTO.setName("Product Test 99");
        productDTO.setCode("999");

        inventoryDTO = new InventoryDTO();
        inventoryDTO.setProductBatchId(1L);
        inventoryDTO.setQuantity(10L);
        inventoryDTO.setObservation("Test Product Input");

        given(productRepository.findById(anyLong())).willReturn(java.util.Optional.ofNullable(products.get(0)));
        given(productBatchRepository.findById(anyLong())).willReturn(java.util.Optional.of(new ProductBatch()));
        willDoNothing().given(productService).inventoryIncrease(any(), any(), anyLong(), anyString());
        willDoNothing().given(productService).inventoryDecrease(any(), any(), anyLong(), anyString());
        given(productMapper.toDomain(any())).willReturn(products.get(0));
        given(productService.save(any())).willReturn(products.get(0));
    }

    @AfterEach
    void tearDown(){
        reset(productService);
    }

    @DisplayName("should return a list of product")
    @Test
    void index() throws Exception {

        given(productService.list(anyInt(), anyInt(), anyString(), anyString())).willReturn(products);

        mockMvc.perform(get("/api/v1/product/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is("Product 01")));
    }

    @DisplayName("Should save a product")
    @Test
    void save() throws Exception {
        mockMvc.perform(post("/api/v1/product/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(productDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @DisplayName("Should not save a product if invalid")
    @Test
    void saveInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/product/")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(new ProductDTO())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("must not be blank")));
    }

    @DisplayName("Should update a product")
    @Test
    void update() throws Exception {
        given(productService.update(anyLong(), any())).willReturn(products.get(0));

        mockMvc.perform(put("/api/v1/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(productDTO)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @DisplayName("Should not update a product")
    @Test
    void updateInvalid() throws Exception {
        mockMvc.perform(put("/api/v1/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(new ProductDTO())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("must not be blank")));
    }

    @DisplayName("Should delete update a product")
    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/product/{id}", 1))
                .andExpect(status().isOk());
    }

    @DisplayName("Should add stock to product")
    @Test
    void inventoryIncrease() throws Exception {
        mockMvc.perform(post("/api/v1/product/{id}/increase", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(inventoryDTO)))
                .andExpect(status().isOk());
    }

    @DisplayName("Should not add stock to product")
    @Test
    void inventoryIncreaseInvalid() throws Exception {
        inventoryDTO.setProductBatchId(null);
        inventoryDTO.setQuantity(0L);

        mockMvc.perform(post("/api/v1/product/{id}/increase", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(inventoryDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.quantity", is("must be greater than or equal to 1")))
                .andExpect(jsonPath("$.productBatchId", is("must not be null")));
    }

    @DisplayName("Should remove stock from product")
    @Test
    void inventoryDecrease() throws Exception {

        mockMvc.perform(post("/api/v1/product/{id}/decrease", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(inventoryDTO)))
                .andExpect(status().isOk());
    }

    @DisplayName("Should not remove stock from product")
    @Test
    void inventoryDecreaseInvalid() throws Exception {
        inventoryDTO.setProductBatchId(null);
        inventoryDTO.setQuantity(0L);

        mockMvc.perform(post("/api/v1/product/{id}/decrease", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(inventoryDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.quantity", is("must be greater than or equal to 1")))
                .andExpect(jsonPath("$.productBatchId", is("must not be null")));
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}