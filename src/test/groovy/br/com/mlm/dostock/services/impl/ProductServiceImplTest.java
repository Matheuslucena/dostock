package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.domain.ProductBatch;
import br.com.mlm.dostock.repositories.ProductBatchRepository;
import br.com.mlm.dostock.repositories.ProductLogRepository;
import br.com.mlm.dostock.repositories.ProductRepository;
import br.com.mlm.dostock.services.ProductBatchService;
import br.com.mlm.dostock.services.ProductLogService;
import br.com.mlm.dostock.util.types.ProductLogType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    public static final String QUANTIDADE_DEVE_SER_MAIOR_QUE_0 = "Quantidade deve ser maior que 0";
    public static final String PRODUTO_COM_ESTOQUE_INSUFICIENTE = "Produto com estoque insuficiente";
    @Mock
    ProductRepository productRepository;

    @Mock
    ProductBatchRepository productBatchRepository;

    @Mock
    ProductLogRepository productLogRepository;

    @Mock
    ProductBatchService productBatchService;

    @Mock
    ProductLogService productLogService;

    @InjectMocks
    ProductServiceImpl productService;

    @Captor
    ArgumentCaptor<Product> productCaptor;

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

    @Test
    @DisplayName("Should list all products")
    void list() {
        productService.list(10,10, "name", "asc");
        verify(productRepository, times(1)).findAll((Pageable) any());
    }

    @Test
    @DisplayName("Should save a product")
    void save() {
        productService.save(new Product());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should update a product")
    void update() {
        Product edit = new Product();
        edit.setName("P1");
        edit.setCode("54321");
        edit.setBatchRequired(true);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        productService.update(1L, edit);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product productUpdated = productCaptor.getValue();

        assertEquals("P1", productUpdated.getName());
        assertEquals("54321", productUpdated.getCode());
        assertTrue(productUpdated.getBatchRequired());
    }

    @Test
    @DisplayName("Should delete a product by id")
    void deleteById() {
        productService.deleteById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should add a product stock")
    void stockInput() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10L);
        productBatch.setValidate(new Date());
        productBatch.setProduct(product1);

        String obs = "Teste";
        Long quantity = 1L;

        productService.stockInput(product1, productBatch, quantity, obs);

        verify(productBatchService, times(1)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, productBatch, quantity, obs, ProductLogType.INPUT);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(11L, saved.getQuantity());
    }

    @Test
    @DisplayName("Should not add item on batch")
    void stockInputNoBatch() {
        product1.setQuantity(10L);

        String obs = "Teste";
        Long quantity = 1L;

        productService.stockInput(product1, null, quantity, obs);

        verify(productBatchService, times(0)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, null, quantity, obs, ProductLogType.INPUT);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(11L, saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when input quantity <= 0")
    void stockInputException() {
        assertThrows(Exception.class, () -> productService.stockInput(null, null, 0L, ""));
    }

    @Test
    @DisplayName("Should remove a product stock")
    void stockOutput() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10L);
        productBatch.setValidate(new Date());
        productBatch.setProduct(product1);

        String obs = "Teste";
        Long quantity = 1L;

        productService.stockOutput(product1, productBatch, quantity, obs);

        verify(productBatchService, times(1)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, productBatch, quantity, obs, ProductLogType.OUTPUT);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(9L, saved.getQuantity());
    }

    @Test
    @DisplayName("Should not remove item on batch")
    void stockOutputNoBatch() {
        product1.setQuantity(10L);

        String obs = "Teste";
        Long quantity = 1L;

        productService.stockOutput(product1, null, quantity, obs);

        verify(productBatchService, times(0)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, null, quantity, obs, ProductLogType.OUTPUT);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(9L, saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when output quantity <= 0")
    void stockOutputException() {
        Throwable exception = assertThrows(Exception.class, () -> productService.stockOutput(null, null, 0L, ""));
        assertEquals(QUANTIDADE_DEVE_SER_MAIOR_QUE_0, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when final quantity < 0")
    void stockQuantityOutputException() {
        product1.setQuantity(-11L);
        Throwable exception = assertThrows(Exception.class, () -> productService.stockOutput(product1, null, 10L, ""));
        assertEquals(PRODUTO_COM_ESTOQUE_INSUFICIENTE, exception.getMessage());
    }
}