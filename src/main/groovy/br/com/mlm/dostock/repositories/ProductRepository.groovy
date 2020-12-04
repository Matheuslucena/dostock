package br.com.mlm.dostock.repositories


import org.springframework.data.repository.PagingAndSortingRepository


interface ProductRepository extends  PagingAndSortingRepository<br.com.mlm.dostock.domain.Product, Long>{
}
