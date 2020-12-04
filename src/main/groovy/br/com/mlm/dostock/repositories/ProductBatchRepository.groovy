package br.com.mlm.dostock.repositories


import org.springframework.data.repository.PagingAndSortingRepository

interface ProductBatchRepository extends PagingAndSortingRepository<br.com.mlm.dostock.domain.ProductBatch, Long>{

    List<br.com.mlm.dostock.domain.ProductBatch> findAllByProduct(br.com.mlm.dostock.domain.Product product)

}