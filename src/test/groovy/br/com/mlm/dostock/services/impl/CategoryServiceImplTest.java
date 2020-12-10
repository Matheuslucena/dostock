package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Category;
import br.com.mlm.dostock.dto.CategoriesDTO;
import br.com.mlm.dostock.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void list() {
        List<Category> parents = new ArrayList<>();
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Computer");

        List<Category> subCategories = new ArrayList<>();
        Category appleCat1 = new Category();
        appleCat1.setId(2L);
        appleCat1.setName("Apple");
        appleCat1.setParentCategory(cat1);
        subCategories.add(appleCat1);

        Category windowsCat1 = new Category();
        windowsCat1.setId(3L);
        windowsCat1.setName("Windows");
        windowsCat1.setParentCategory(cat1);
        subCategories.add(windowsCat1);

        Category cat2 = new Category();
        cat2.setId(4L);
        cat2.setName("Monitor");

        parents.add(cat1);
        parents.add(cat2);

        given(categoryRepository.findAllByParentCategoryIsNull()).willReturn(parents);
        given(categoryRepository.findAllByParentCategory(cat1)).willReturn(subCategories);
        given(categoryRepository.findAllByParentCategory(appleCat1)).willReturn(new ArrayList<>());
        given(categoryRepository.findAllByParentCategory(windowsCat1)).willReturn(new ArrayList<>());
        given(categoryRepository.findAllByParentCategory(cat2)).willReturn(new ArrayList<>());

        List<CategoriesDTO> result = categoryService.list();

        assertEquals("Computer", result.get(0).getCategory().get("name"));
        assertEquals(2, result.size());
        assertEquals("Windows", result.get(0).getSubCategories().get(1).getCategory().get("name"));
    }

    @Test
    void save() {
        categoryService.save(new Category());
        then(categoryRepository).should(times(1)).save(any());
    }

    @Test
    void update() {
        ArgumentCaptor<Category> categoryArgumentCaptor = ArgumentCaptor.forClass(Category.class);
        Category cat1 = new Category();
        cat1.setId(1L);
        cat1.setName("Computer");
        given(categoryRepository.findById(any())).willReturn(java.util.Optional.of(new Category()));

        categoryService.update(cat1);

        then(categoryRepository).should(times(1)).save(categoryArgumentCaptor.capture());
        Category category = categoryArgumentCaptor.getValue();
        assertEquals("Computer", category.getName());
        assertNull(category.getParentCategory());
    }

    @Test
    void deleteById() {
        categoryService.deleteById(1L);
        then(categoryRepository).should(times(1)).deleteById(1L);
    }
}