package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.dto.InventoryDTO
import br.com.mlm.dostock.dto.ProductDTO
import br.com.mlm.dostock.dto.mapper.ProductMapper
import br.com.mlm.dostock.repositories.ProductBatchRepository
import br.com.mlm.dostock.repositories.ProductRepository
import br.com.mlm.dostock.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RequestMapping("/api/v1/product")
@RestController
class ProductController {

    ProductService productService

    ProductRepository productRepository

    ProductBatchRepository productBatchRepository

    ProductMapper productMapper

    ProductController(ProductService productService, ProductRepository productRepository, ProductBatchRepository productBatchRepository, ProductMapper productMapper) {
        this.productService = productService
        this.productRepository = productRepository
        this.productBatchRepository = productBatchRepository
        this.productMapper = productMapper
    }

    @GetMapping("/")
    List<Product> index(@RequestParam(defaultValue = "15") Integer max, @RequestParam(defaultValue = "0") Integer offset,
                        @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "asc") String order){
        return productService.list(max, offset, sort, order)
    }

    @PostMapping("/")
    Product save(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toDomain(productDTO)
        return productService.save(product)
    }

    @PutMapping("/{id}")
    Product update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toDomain(productDTO)
        return productService.update(id, product)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        productService.deleteById(id)
    }

    @PostMapping("/{id}/increase")
    @ResponseStatus(HttpStatus.OK)
    void inventoryIncrease(@PathVariable Long id, @Valid @RequestBody InventoryDTO inventoryDTO){
        Product product = productRepository.findById(id).orElse(null)
        ProductBatch productBatch = productBatchRepository.findById(inventoryDTO.productBatchId).orElse(null)
        productService.inventoryIncrease(product, productBatch, inventoryDTO.quantity, inventoryDTO.observation)
    }

    @PostMapping("/{id}/decrease")
    @ResponseStatus(HttpStatus.OK)
    void inventoryDecrease(@PathVariable Long id, @Valid @RequestBody InventoryDTO inventoryDTO){
        Product product = productRepository.findById(id).orElse(null)
        ProductBatch productBatch = productBatchRepository.findById(inventoryDTO.productBatchId).orElse(null)
        productService.inventoryDecrease(product, productBatch, inventoryDTO.quantity, inventoryDTO.observation)
    }
}
