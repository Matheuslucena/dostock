package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Tag;
import br.com.mlm.dostock.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void save() {
        tagService.save(new Tag());
        verify(tagRepository, times(1)).save(any());
    }

    @Test
    void saveAll() {
        tagService.saveAll(new HashSet<>());
        verify(tagRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void deleteById() {
        tagService.deleteById(1L);
        verify(tagRepository, times(1)).deleteById(anyLong());
    }
}