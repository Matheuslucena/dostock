package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.domain.ProductLog
import br.com.mlm.dostock.util.types.ProductLogType

interface ProductLogService {

    List<ProductLog> list(Integer max, Integer offset, String sort, String order)

    void register(Product product, ProductBatch productBatch, Integer quantity, String observation, ProductLogType productLogType)

}