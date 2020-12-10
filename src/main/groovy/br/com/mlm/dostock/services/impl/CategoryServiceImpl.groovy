package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Category
import br.com.mlm.dostock.dto.CategoriesDTO
import br.com.mlm.dostock.repositories.CategoryRepository
import br.com.mlm.dostock.services.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl implements CategoryService{
    CategoryRepository categoryRepository

    CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository
    }

    @Override
    List<CategoriesDTO> list() {
        List<Category> parent = categoryRepository.findAllByParentCategoryIsNull()
        return categoryList(parent)
    }

    private List<CategoriesDTO> categoryList(List<Category> categories){
        List<CategoriesDTO> result = []
        categories?.sort{it.name}?.each { Category cat ->
            CategoriesDTO categoriesDTO = new CategoriesDTO([
                    category: [id: cat.id, name: cat.name],
                    subCategories: categoryList(categoryRepository.findAllByParentCategory(cat))
            ])
            result.add(categoriesDTO)
        }
        return result
    }

    @Override
    Category save(Category category) {
        return categoryRepository.save(category)
    }

    @Override
    Category update(Category category) {
        Category categoryToSave = categoryRepository.findById(category.id).orElse(null)
        categoryToSave.name = category.name
        categoryToSave.parentCategory = category.parentCategory
        return categoryRepository.save(categoryToSave)
    }

    @Override
    void deleteById(Long id) {
        categoryRepository.deleteById(id)
    }
}
