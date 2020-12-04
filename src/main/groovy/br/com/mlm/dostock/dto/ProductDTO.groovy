package br.com.mlm.dostock.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class ProductDTO {
    @NotNull
    String name

    @NotBlank
    String code

    @Min(0L)
    Long quantity

    @Min(0L)
    Long minimumLevel

    Boolean batchRequired

    String observation
}
