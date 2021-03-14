package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.dto.FolderDTO
import br.com.mlm.dostock.dto.FoldersDTO
import br.com.mlm.dostock.dto.mapper.FolderMapper
import br.com.mlm.dostock.services.FolderService
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RequestMapping("/api/v1/folder")
@RestController
class FolderController {

    FolderService folderService

    FolderMapper folderMapper

    FolderController(FolderService folderService, FolderMapper folderMapper) {
        this.folderService = folderService
        this.folderMapper = folderMapper
    }

    @GetMapping("/")
    List<FoldersDTO> list(){
        return folderService.list()
    }

    @GetMapping("/listAll")
    List<Folder> listAll() {
        return folderService.listAll()?.sort{it.name}
    }

    @PostMapping("/")
    Folder save(@Valid @RequestBody FolderDTO folderDTO){
        Folder folder = folderMapper.toDomain(folderDTO)
        return folderService.save(folder)
    }

    @PutMapping("/{id}")
    Folder update(@PathVariable Long id, @Valid @RequestBody FolderDTO folderDTO){
        Folder folder = folderMapper.toDomain(folderDTO)
        return folderService.update(id, folder)
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id){
        folderService.deleteById(id)
    }
}
