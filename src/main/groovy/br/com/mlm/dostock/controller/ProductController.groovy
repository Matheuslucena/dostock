package br.com.mlm.dostock.controller


import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.dto.ProductDTO
import br.com.mlm.dostock.dto.StockDTO
import br.com.mlm.dostock.repositories.ProductBatchRepository
import br.com.mlm.dostock.repositories.ProductRepository
import br.com.mlm.dostock.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
    List<br.com.mlm.dostock.domain.Product> index(@RequestParam(defaultValue = "15") Integer max, @RequestParam(defaultValue = "0") Integer offset,
                                                  @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "asc") String order){
        return productService.list(max, offset, sort, order)
    }

    @PostMapping("/")
    br.com.mlm.dostock.domain.Product save(@Valid @RequestBody ProductDTO product) {
        return productService.save(product)
    }

    @PutMapping("/{id}")
    br.com.mlm.dostock.domain.Product update(@PathVariable Long id, @RequestBody br.com.mlm.dostock.domain.Product product) {
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
        br.com.mlm.dostock.domain.Product product = productRepository.findById(id) as br.com.mlm.dostock.domain.Product
        ProductBatch productBatch = productBatchRepository.findById(stockDTO.productBatchId) as ProductBatch
        productService.stockInput(product, productBatch, stockDTO.quantity, stockDTO.observation)
    }

    @PostMapping("/{id}/stockOutput")
    @ResponseStatus(HttpStatus.OK)
    void stockOutput(@PathVariable Long id, @RequestBody StockDTO stockDTO){
        br.com.mlm.dostock.domain.Product product = productRepository.findById(id) as br.com.mlm.dostock.domain.Product
        ProductBatch productBatch = productBatchRepository.findById(stockDTO.productBatchId) as ProductBatch
        productService.stockOutput(product, productBatch, stockDTO.quantity, stockDTO.observation)
    }
}
