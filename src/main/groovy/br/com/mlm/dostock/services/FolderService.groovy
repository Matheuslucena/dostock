package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.dto.FoldersDTO

interface FolderService {
    List<FoldersDTO> list()
    List<Folder> listAll()
    Folder save(Folder folder)
    Folder update(Long id, Folder folder)
    void deleteById(Long id)
}