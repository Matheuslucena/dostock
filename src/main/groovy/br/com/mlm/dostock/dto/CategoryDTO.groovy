package br.com.mlm.dostock.dto

import javax.validation.constraints.NotBlank

class CategoryDTO {
    Long id

    @NotBlank
    String name

    CategoryDTO parentCatetory
}
