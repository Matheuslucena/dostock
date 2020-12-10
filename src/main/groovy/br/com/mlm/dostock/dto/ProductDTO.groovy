package br.com.mlm.dostock.dto

import br.com.mlm.dostock.domain.Tag

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class ProductDTO {
    @NotBlank
    String name

    String code

    @Min(0L)
    Integer minimumLevel

    Boolean batchRequired = Boolean.FALSE

    Set<Tag> tags = []

    String observation

    CategoryDTO category
}
