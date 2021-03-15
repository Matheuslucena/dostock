package br.com.mlm.dostock.dto

import org.springframework.web.multipart.MultipartFile

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class ProductDTO {
    @NotBlank
    String name

    String code

    @Min(0L)
    BigDecimal minimumLevel

    Boolean batchRequired = Boolean.FALSE

    //Set<Tag> tags = []

    String observation

    MultipartFile image
}
