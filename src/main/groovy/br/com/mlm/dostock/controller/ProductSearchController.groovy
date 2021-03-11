package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.dto.ProductSearchDTO
import br.com.mlm.dostock.services.ProductService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/productSearch")
@RestController
class ProductSearchController {

    ProductService productService

    ProductSearchController(ProductService productService) {
        this.productService = productService
    }

    @PostMapping("/")
    List<Product> search(@RequestBody ProductSearchDTO searchDTO){
        return productService.search(searchDTO.name, searchDTO.code, searchDTO.tagId, searchDTO.folderId)
    }

}
