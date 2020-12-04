package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.domain.ProductBatch;
import br.com.mlm.dostock.repositories.ProductBatchRepository;
import org.junit.jupiter.api.BeforeEach;
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
class ProductBatchServiceImplTest {

    @Mock
    ProductBatchRepository productBatchRepository;

    @InjectMocks
    ProductBatchServiceImpl productBatchService;

    @Captor
    ArgumentCaptor<ProductBatch> productBatchArgumentCaptor;

    ProductBatch productBatch;

    @BeforeEach
    void setUp() {
        productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10L);
        productBatch.setValidate(new Date());
        productBatch.setProduct(new Product());
    }

    @Test
    @DisplayName("Should list batches by product")
    void list() {
        productBatchService.list(new Product());
        verify(productBatchRepository, times(1)).findAllByProduct(any());
    }

    @Test
    @DisplayName("Should save a batch")
    void save() {
        productBatchService.save(productBatch);
        verify(productBatchRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should add quantity to batch")
    void addQuantity() {
        Long quantity = 10L;
        productBatchService.addQuantity(productBatch, quantity);

        verify(productBatchRepository, times(1)).save(productBatchArgumentCaptor.capture());
        ProductBatch saved = productBatchArgumentCaptor.getValue();

        assertEquals(20L, saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception if quantity less or equal to 0")
    void addQuantityThrowException() {
        Long quantity = 0L;

        assertThrows(Exception.class, () -> {
            productBatchService.addQuantity(productBatch, quantity);
        });
    }

    @Test
    @DisplayName("Should remove quantity from batch")
    void removeQuantity() {
        Long quantity = 10L;
        productBatchService.removeQuantity(productBatch, quantity);

        verify(productBatchRepository, times(1)).save(productBatchArgumentCaptor.capture());
        ProductBatch saved = productBatchArgumentCaptor.getValue();

        assertEquals(0L, saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception if quantity less or equal to 0")
    void removeQuantityThrowException() {
        Long quantity = 0L;

        Throwable exception = assertThrows(Exception.class, () -> {
            productBatchService.removeQuantity(productBatch, quantity);
        });

        assertEquals("Quantidade deve ser maior que 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if insufficient quantity")
    void removeQuantityThrowExceptionQuantity() {
        Long quantity = 11L;

        Throwable exception = assertThrows(Exception.class, () -> {
            productBatchService.removeQuantity(productBatch, quantity);
        });

        assertEquals("Produto com estoque insuficiente", exception.getMessage());
    }
}