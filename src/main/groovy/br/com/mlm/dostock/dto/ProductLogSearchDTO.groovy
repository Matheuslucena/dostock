package br.com.mlm.dostock.dto

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.util.types.ProductLogType

class ProductLogSearchDTO {
    Date dateInitial
    Date dateFinal
    Product product
    ProductLogType logType = ProductLogType.INCREASE
}
