package br.com.mlm.dostock.services.impl;

import br.com.mlm.dostock.domain.Folder;
import br.com.mlm.dostock.dto.FoldersDTO;
import br.com.mlm.dostock.repositories.FolderRepository;
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
class FolderServiceImplTest {
    
    @Mock
    FolderRepository folderRepository;
    
    @InjectMocks
    FolderServiceImpl folderService;
            

    @Test
    void list() {
        List<Folder> parents = new ArrayList<>();
        Folder folder1 = new Folder();
        folder1.setId(1L);
        folder1.setName("Computer");

        List<Folder> subCategories = new ArrayList<>();
        Folder appleCat1 = new Folder();
        appleCat1.setId(2L);
        appleCat1.setName("Apple");
        appleCat1.setParentFolder(folder1);
        subCategories.add(appleCat1);

        Folder windowsCat1 = new Folder();
        windowsCat1.setId(3L);
        windowsCat1.setName("Windows");
        windowsCat1.setParentFolder(folder1);
        subCategories.add(windowsCat1);

        Folder folder2 = new Folder();
        folder2.setId(4L);
        folder2.setName("Monitor");

        parents.add(folder1);
        parents.add(folder2);

        given(folderRepository.findAllByParentFolderIsNull()).willReturn(parents);
        given(folderRepository.findAllByParentFolder(folder1)).willReturn(subCategories);
        given(folderRepository.findAllByParentFolder(appleCat1)).willReturn(new ArrayList<>());
        given(folderRepository.findAllByParentFolder(windowsCat1)).willReturn(new ArrayList<>());
        given(folderRepository.findAllByParentFolder(folder2)).willReturn(new ArrayList<>());

        List<FoldersDTO> result = folderService.list();

        assertEquals("Computer", result.get(0).getFolder().get("name"));
        assertEquals(2, result.size());
        assertEquals("Windows", result.get(0).getSubFolders().get(1).getFolder().get("name"));
    }

    @Test
    void save() {
        folderService.save(new Folder());
        then(folderRepository).should(times(1)).save(any(Folder.class));
    }

    @Test
    void update() {
        ArgumentCaptor<Folder> categoryArgumentCaptor = ArgumentCaptor.forClass(Folder.class);
        Folder folder1 = new Folder();
        folder1.setId(1L);
        folder1.setName("Computer");
        given(folderRepository.findById(any())).willReturn(java.util.Optional.of(new Folder()));

        folderService.update(folder1);

        then(folderRepository).should(times(1)).save(categoryArgumentCaptor.capture());
        Folder category = categoryArgumentCaptor.getValue();
        assertEquals("Computer", category.getName());
        assertNull(category.getParentFolder());
    }

    @Test
    void deleteById() {
        folderService.deleteById(1L);
        then(folderRepository).should(times(1)).deleteById(1L);
    }
}