package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.ProductLog
import br.com.mlm.dostock.dto.ProductLogSearchDTO
import br.com.mlm.dostock.services.ProductLogService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1/product/log")
@RestController
class ProductLogController {

    ProductLogService productLogService

    ProductLogController(ProductLogService productLogService) {
        this.productLogService = productLogService
    }

    @PostMapping("/search")
    List<ProductLog> search(@RequestBody ProductLogSearchDTO searchDTO){
        return productLogService.search(searchDTO.dateInitial, searchDTO.dateFinal, searchDTO.product, searchDTO.logType)
    }
}
