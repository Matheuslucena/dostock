package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Tag
import org.springframework.data.repository.PagingAndSortingRepository

interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

}