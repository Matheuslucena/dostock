package br.com.mlm.dostock.dto.mapper

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.dto.FolderDTO
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class FolderMapper {

    ModelMapper modelMapper

    FolderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper
    }

    FolderDTO toDTO(Folder folder){
        return modelMapper.map(folder, FolderDTO)
    }

    Folder toDomain(FolderDTO folderDTO){
        return modelMapper.map(folderDTO, Folder)
    }
}
