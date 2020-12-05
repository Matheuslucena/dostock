package br.com.mlm.dostock.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class ProductDTO {
    @NotBlank
    String name

    String code

    @Min(0L)
    Integer quantity

    @Min(0L)
    Integer minimumLevel

    Boolean batchRequired = Boolean.FALSE

    String observation
}
