package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Category
import org.springframework.data.repository.PagingAndSortingRepository

interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
    List<Category> findAllByParentCategoryIsNull()
    List<Category> findAllByParentCategory(Category category)
}