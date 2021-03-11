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

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        productBatch.setQuantity(new BigDecimal(10));
        productBatch.setExpirationDate(new Date());
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
        BigDecimal quantity = new BigDecimal(10);
        productBatchService.addQuantity(productBatch, quantity);

        verify(productBatchRepository, times(1)).save(productBatchArgumentCaptor.capture());
        ProductBatch saved = productBatchArgumentCaptor.getValue();

        assertEquals(new BigDecimal(20), saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception if quantity less or equal to 0")
    void addQuantityThrowException() {
        BigDecimal quantity = new BigDecimal(0);

        assertThrows(Exception.class, () -> productBatchService.addQuantity(productBatch, quantity));
    }

    @Test
    @DisplayName("Should remove quantity from batch")
    void removeQuantity() {
        BigDecimal quantity = new BigDecimal(10);
        productBatchService.removeQuantity(productBatch, quantity);

        verify(productBatchRepository, times(1)).save(productBatchArgumentCaptor.capture());
        ProductBatch saved = productBatchArgumentCaptor.getValue();

        assertEquals(new BigDecimal(0), saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception if quantity less or equal to 0")
    void removeQuantityThrowException() {
        BigDecimal quantity = new BigDecimal(0);

        Throwable exception = assertThrows(Exception.class, () -> productBatchService.removeQuantity(productBatch, quantity));

        assertEquals("Quantidade deve ser maior que 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception if insufficient quantity")
    void removeQuantityThrowExceptionQuantity() {
        BigDecimal quantity = new BigDecimal(11);

        Throwable exception = assertThrows(Exception.class, () -> productBatchService.removeQuantity(productBatch, quantity));

        assertEquals("Produto com estoque insuficiente", exception.getMessage());
    }
}