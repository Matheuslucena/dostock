package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductLog
import br.com.mlm.dostock.util.types.ProductLogType

interface ProductLogRepositoryCustom {
    List<ProductLog> search(Date dateInitial, Date dateFinal, Product product, ProductLogType productLogType)
}