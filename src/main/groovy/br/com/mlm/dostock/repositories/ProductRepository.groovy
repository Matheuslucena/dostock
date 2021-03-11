package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Product
import org.springframework.data.repository.PagingAndSortingRepository


interface ProductRepository extends PagingAndSortingRepository<Product, Long>, ProductRepositoryCustom{
}
