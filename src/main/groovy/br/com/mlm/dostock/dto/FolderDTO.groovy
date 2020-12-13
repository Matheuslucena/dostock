package br.com.mlm.dostock.dto

import javax.validation.constraints.NotBlank

class FolderDTO {
    Long id

    @NotBlank
    String name

    FolderDTO parentFolder
}
