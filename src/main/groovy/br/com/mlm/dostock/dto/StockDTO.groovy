package br.com.mlm.dostock.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class StockDTO {

    @NotNull
    Long productBatchId

    @Min(1L)
    @NotNull
    Long quantity
    String observation
}
