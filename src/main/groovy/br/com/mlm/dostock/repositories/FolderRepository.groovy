package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Folder
import org.springframework.data.repository.PagingAndSortingRepository

interface FolderRepository extends PagingAndSortingRepository<Folder, Long>{
    List<Folder> findAllByParentFolderIsNull()
    List<Folder> findAllByParentFolder(Folder folder)
    List<Folder> findAllByDeleted(Boolean deleted)
}