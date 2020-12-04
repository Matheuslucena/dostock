package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import org.springframework.data.repository.PagingAndSortingRepository

interface ProductBatchRepository extends PagingAndSortingRepository<ProductBatch, Long>{

    List<ProductBatch> findAllByProduct(Product product)

}