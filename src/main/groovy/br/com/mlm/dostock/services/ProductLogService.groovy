package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.domain.ProductLog
import br.com.mlm.dostock.util.types.ProductLogType

interface ProductLogService {

    List<ProductLog> search(Date dateInitial, Date dateFinal, Product product, ProductLogType logType)

    void register(Product product, Folder folder, ProductBatch productBatch, BigDecimal quantity, String observation, ProductLogType productLogType)

}