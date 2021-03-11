package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.dto.FoldersDTO
import br.com.mlm.dostock.repositories.FolderRepository
import br.com.mlm.dostock.services.FolderService
import org.springframework.stereotype.Service

@Service
class FolderServiceImpl implements FolderService{
    
    FolderRepository folderRepository

    FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository
    }

    @Override
    List<FoldersDTO> list() {
        List<Folder> parent = folderRepository.findAllByParentFolderIsNull()
        return folderList(parent)
    }

    private List<FoldersDTO> folderList(List<Folder> folders){
        List<FoldersDTO> result = []
        folders?.sort{it.name}?.each { Folder folder ->
            FoldersDTO foldersDTO = new FoldersDTO([
                    folder: [id: folder.id, name: folder.name],
                    subFolders: folderList(folderRepository.findAllByParentFolder(folder))
            ])
            result.add(foldersDTO)
        }
        return result
    }

    @Override
    Folder save(Folder folder) {
        return folderRepository.save(folder)
    }

    @Override
    Folder update(Long id, Folder folder) {
        Folder folderToSave = folderRepository.findById(id).orElse(null)
        folderToSave.name = folder.name
        folderToSave.parentFolder = folder.parentFolder
        return folderRepository.save(folderToSave)
    }

    @Override
    void deleteById(Long id) {
        folderRepository.deleteById(id)
    }
}
