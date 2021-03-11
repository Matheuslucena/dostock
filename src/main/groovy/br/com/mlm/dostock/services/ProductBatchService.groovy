package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch

interface ProductBatchService {

    List<ProductBatch> list(Product product)

    ProductBatch save(ProductBatch productBatch)

    ProductBatch addQuantity(ProductBatch productBatch, BigDecimal quantity)

    ProductBatch removeQuantity(ProductBatch productBatch, BigDecimal quantity)

}