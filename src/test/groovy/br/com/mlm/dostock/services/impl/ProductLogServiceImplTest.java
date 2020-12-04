package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.domain.ProductBatch;
import br.com.mlm.dostock.domain.ProductLog;
import br.com.mlm.dostock.repositories.ProductLogRepository;
import br.com.mlm.dostock.util.types.ProductLogType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductLogServiceImplTest {

    @Mock
    ProductLogRepository productLogRepository;

    @InjectMocks
    ProductLogServiceImpl productLogService;

    @Captor
    ArgumentCaptor<ProductLog> productLogArgumentCaptor;

    Product product1;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setName("Produto teste 01");
        product1.setCode("12345");
        product1.setMinimumLevel(10L);
        product1.setBatchRequired(false);
        product1.setObservation("Observacao produto 01");
        product1.setQuantity(10L);
    }

    @Disabled
    @Test
    void list() {
    }

    @Test
    @DisplayName("Should register a stock input")
    void registerInput() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10L);
        productBatch.setValidate(new Date());
        productBatch.setProduct(product1);

        Long quantity = 5L;
        String observation = "New Input";

        productLogService.register(product1, productBatch, quantity, observation, ProductLogType.INPUT);
        verify(productLogRepository, times(1)).save(productLogArgumentCaptor.capture());

        ProductLog productLog = productLogArgumentCaptor.getValue();

        assertEquals(ProductLogType.INPUT, productLog.getLogType());
        assertEquals(quantity, productLog.getQuantity());
    }

    @Test
    @DisplayName("Should register a stock output")
    void registerOutput() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10L);
        productBatch.setValidate(new Date());
        productBatch.setProduct(product1);

        Long quantity = 5L;
        String observation = "New Input";

        productLogService.register(product1, productBatch, quantity, observation, ProductLogType.OUTPUT);
        verify(productLogRepository, times(1)).save(productLogArgumentCaptor.capture());

        ProductLog productLog = productLogArgumentCaptor.getValue();

        assertEquals(ProductLogType.OUTPUT, productLog.getLogType());
        assertEquals(quantity, productLog.getQuantity());
    }
}