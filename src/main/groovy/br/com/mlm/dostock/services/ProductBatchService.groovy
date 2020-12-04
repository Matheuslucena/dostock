package br.com.mlm.dostock.services

interface ProductBatchService {

    List<br.com.mlm.dostock.domain.ProductBatch> list(br.com.mlm.dostock.domain.Product product)

    br.com.mlm.dostock.domain.ProductBatch save(br.com.mlm.dostock.domain.ProductBatch productBatch)

    br.com.mlm.dostock.domain.ProductBatch addQuantity(br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity)

    br.com.mlm.dostock.domain.ProductBatch removeQuantity(br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity)

}