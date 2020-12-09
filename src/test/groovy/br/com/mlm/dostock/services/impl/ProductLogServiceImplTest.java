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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        product1.setMinimumLevel(10);
        product1.setBatchRequired(false);
        product1.setObservation("Observacao produto 01");
        product1.setQuantity(10);
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
        productBatch.setQuantity(10);
        productBatch.setExpirationDate(new Date());
        productBatch.setProduct(product1);

        Integer quantity = 5;
        String observation = "New Input";

        productLogService.register(product1, productBatch, quantity, observation, ProductLogType.INCREASE);
        verify(productLogRepository, times(1)).save(productLogArgumentCaptor.capture());

        ProductLog productLog = productLogArgumentCaptor.getValue();

        assertEquals(ProductLogType.INCREASE, productLog.getLogType());
        assertEquals(quantity, productLog.getQuantity());
    }

    @Test
    @DisplayName("Should register a stock output")
    void registerOutput() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10);
        productBatch.setExpirationDate(new Date());
        productBatch.setProduct(product1);

        Integer quantity = 5;
        String observation = "New Input";

        productLogService.register(product1, productBatch, quantity, observation, ProductLogType.DECREASE);
        verify(productLogRepository, times(1)).save(productLogArgumentCaptor.capture());

        ProductLog productLog = productLogArgumentCaptor.getValue();

        assertEquals(ProductLogType.DECREASE, productLog.getLogType());
        assertEquals(quantity, productLog.getQuantity());
    }
}