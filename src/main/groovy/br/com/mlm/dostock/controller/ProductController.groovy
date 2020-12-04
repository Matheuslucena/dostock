package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.dto.StockDTO
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

    ProductController(ProductService productService, ProductRepository productRepository, ProductBatchRepository productBatchRepository) {
        this.productService = productService
        this.productRepository = productRepository
        this.productBatchRepository = productBatchRepository
    }

    @GetMapping("/")
    List<Product> index(@RequestParam(defaultValue = "15") Integer max, @RequestParam(defaultValue = "0") Integer offset,
                        @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "asc") String order){
        return productService.list(max, offset, sort, order)
    }

    @PostMapping("/")
    Product save(@Valid @RequestBody Product product) {
        return productService.save(product)
    }

    @PutMapping("/{id}")
    Product update(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        productService.deleteById(id)
    }

    @PostMapping("/{id}/stockInput")
    @ResponseStatus(HttpStatus.OK)
    void stockInput(@PathVariable Long id, @RequestBody StockDTO stockDTO){
        Product product = productRepository.findById(id) as Product
        ProductBatch productBatch = productBatchRepository.findById(stockDTO.productBatchId) as ProductBatch
        productService.stockInput(product, productBatch, stockDTO.quantity, stockDTO.observation)
    }

    @PostMapping("/{id}/stockOutput")
    @ResponseStatus(HttpStatus.OK)
    void stockOutput(@PathVariable Long id, @RequestBody StockDTO stockDTO){
        Product product = productRepository.findById(id) as Product
        ProductBatch productBatch = productBatchRepository.findById(stockDTO.productBatchId) as ProductBatch
        productService.stockOutput(product, productBatch, stockDTO.quantity, stockDTO.observation)
    }
}
