package br.com.mlm.dostock.repositories


import org.springframework.data.repository.PagingAndSortingRepository

interface ProductLogRepository extends PagingAndSortingRepository<br.com.mlm.dostock.domain.ProductLog, Long>{

}