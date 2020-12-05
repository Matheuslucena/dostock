package br.com.mlm.dostock.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class InventoryDTO {

    @NotNull
    Long productBatchId

    @Min(1L)
    @NotNull
    Integer quantity
    String observation
}
