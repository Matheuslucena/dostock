package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.ProductLog
import org.springframework.data.repository.PagingAndSortingRepository

interface ProductLogRepository extends PagingAndSortingRepository<ProductLog, Long>, ProductLogRepositoryCustom{

}