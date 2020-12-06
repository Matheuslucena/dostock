package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.domain.ProductBatch;
import br.com.mlm.dostock.domain.Tag;
import br.com.mlm.dostock.repositories.ProductBatchRepository;
import br.com.mlm.dostock.repositories.ProductLogRepository;
import br.com.mlm.dostock.repositories.ProductRepository;
import br.com.mlm.dostock.services.ProductBatchService;
import br.com.mlm.dostock.services.ProductLogService;
import br.com.mlm.dostock.services.TagService;
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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Mock
    TagService tagService;

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
        product1.setMinimumLevel(10);
        product1.setBatchRequired(false);
        product1.setObservation("Observacao produto 01");
        product1.setQuantity(10);
        Set<Tag> tags = new HashSet<>();
        Tag tag1 = new Tag();
        tag1.setName("Tag 01");
        tags.add(tag1);
        product1.setTags(tags);
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
        productService.save(product1);
        verify(tagService, times(1)).saveAll(anySet());
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
    void inventoryIncrease() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10);
        productBatch.setValidate(new Date());
        productBatch.setProduct(product1);

        String obs = "Teste";
        Integer quantity = 1;

        productService.inventoryIncrease(product1, productBatch, quantity, obs);

        verify(productBatchService, times(1)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, productBatch, quantity, obs, ProductLogType.INCREASE);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(11, saved.getQuantity());
    }

    @Test
    @DisplayName("Should not add item on batch")
    void inventoryIncreaseNoBatch() {
        product1.setQuantity(10);

        String obs = "Teste";
        Integer quantity = 1;

        productService.inventoryIncrease(product1, null, quantity, obs);

        verify(productBatchService, times(0)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, null, quantity, obs, ProductLogType.INCREASE);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(11, saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when input quantity <= 0")
    void inventoryIncreaseException() {
        assertThrows(Exception.class, () -> productService.inventoryIncrease(null, null, 0, ""));
    }

    @Test
    @DisplayName("Should remove a product stock")
    void inventoryDecrease() {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setId(1L);
        productBatch.setQuantity(10);
        productBatch.setValidate(new Date());
        productBatch.setProduct(product1);

        String obs = "Teste";
        Integer quantity = 1;

        productService.inventoryDecrease(product1, productBatch, quantity, obs);

        verify(productBatchService, times(1)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, productBatch, quantity, obs, ProductLogType.DECREASE);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(9, saved.getQuantity());
    }

    @Test
    @DisplayName("Should not remove item on batch")
    void inventoryDecreaseNoBatch() {
        product1.setQuantity(10);

        String obs = "Teste";
        Integer quantity = 1;

        productService.inventoryDecrease(product1, null, quantity, obs);

        verify(productBatchService, times(0)).addQuantity(any(), any());
        verify(productLogService, times(1)).register(product1, null, quantity, obs, ProductLogType.DECREASE);
        verify(productRepository, times(1)).save(productCaptor.capture());

        Product saved = productCaptor.getValue();
        assertEquals(9, saved.getQuantity());
    }

    @Test
    @DisplayName("Should throw exception when output quantity <= 0")
    void inventoryDecreaseException() {
        Throwable exception = assertThrows(Exception.class, () -> productService.inventoryDecrease(null, null, 0, ""));
        assertEquals(QUANTIDADE_DEVE_SER_MAIOR_QUE_0, exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when final quantity < 0")
    void stockQuantityOutputException() {
        product1.setQuantity(-11);
        Throwable exception = assertThrows(Exception.class, () -> productService.inventoryDecrease(product1, null, 10, ""));
        assertEquals(PRODUTO_COM_ESTOQUE_INSUFICIENTE, exception.getMessage());
    }
}