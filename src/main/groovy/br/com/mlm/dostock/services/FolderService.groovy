package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.dto.FoldersDTO

interface FolderService {
    List<FoldersDTO> list()
    Folder save(Folder folder)
    Folder update(Folder folder)
    void deleteById(Long id)
}