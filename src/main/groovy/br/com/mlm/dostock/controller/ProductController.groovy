package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.dto.ProductDTO
import br.com.mlm.dostock.dto.StockDTO
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

    @PostMapping("/{id}/stockInput")
    @ResponseStatus(HttpStatus.OK)
    void stockInput(@PathVariable Long id, @Valid @RequestBody StockDTO stockDTO){
        Product product = productRepository.findById(id).orElse(null)
        ProductBatch productBatch = productBatchRepository.findById(stockDTO.productBatchId).orElse(null)
        productService.stockInput(product, productBatch, stockDTO.quantity, stockDTO.observation)
    }

    @PostMapping("/{id}/stockOutput")
    @ResponseStatus(HttpStatus.OK)
    void stockOutput(@PathVariable Long id, @Valid @RequestBody StockDTO stockDTO){
        Product product = productRepository.findById(id).orElse(null)
        ProductBatch productBatch = productBatchRepository.findById(stockDTO.productBatchId).orElse(null)
        productService.stockOutput(product, productBatch, stockDTO.quantity, stockDTO.observation)
    }
}
