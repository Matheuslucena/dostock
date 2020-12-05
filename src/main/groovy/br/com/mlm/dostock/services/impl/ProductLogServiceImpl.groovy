package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.domain.ProductLog
import br.com.mlm.dostock.repositories.ProductLogRepository
import br.com.mlm.dostock.services.ProductLogService
import br.com.mlm.dostock.util.types.ProductLogType
import org.springframework.stereotype.Service

@Service
class ProductLogServiceImpl implements ProductLogService {

    ProductLogRepository productLogRepository

    ProductLogServiceImpl(ProductLogRepository productLogRepository) {
        this.productLogRepository = productLogRepository
    }

    @Override
    List<ProductLog> list(Integer max, Integer offset, String sort, String order) {
        return null
    }

    @Override
    void register(Product product, ProductBatch productBatch, Integer quantity, String observation, ProductLogType logType) {
        ProductLog productLog = new ProductLog()
        productLog.quantity = quantity
        productLog.observation = observation
        productLog.product = product
        productLog.productBatch = productBatch
        productLog.logType = logType

        productLogRepository.save(productLog)
    }
}
