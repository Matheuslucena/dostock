package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Category
import br.com.mlm.dostock.dto.CategoriesDTO

interface CategoryService {
    List<CategoriesDTO> list()
    Category save(Category category)
    Category update(Category category)
    void deleteById(Long id)
}