package br.com.mlm.dostock.dto

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class InventoryDTO {
    ProductBatchDTO productBatch

    FolderDTO folder

    @Min(1L)
    @NotNull
    BigDecimal quantity

    String observation
}
