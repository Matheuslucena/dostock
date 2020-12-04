package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.repositories.ProductBatchRepository
import br.com.mlm.dostock.services.ProductBatchService
import org.springframework.stereotype.Service

@Service
class ProductBatchServiceImpl implements ProductBatchService{

    ProductBatchRepository productBatchRepository

    ProductBatchServiceImpl(ProductBatchRepository productBatchRepository) {
        this.productBatchRepository = productBatchRepository
    }

    @Override
    List<ProductBatch> list(Product product) {
        return productBatchRepository.findAllByProduct(product)
    }

    @Override
    ProductBatch save(ProductBatch productBatch) {
        return productBatchRepository.save(productBatch)
    }

    @Override
    ProductBatch addQuantity(ProductBatch productBatch, Long quantity) {
        if(quantity <= 0 ){
            throw new Exception("Quantidade deve ser maior que 0")
        }
        productBatch.quantity += quantity
        return productBatchRepository.save(productBatch)
    }

    @Override
    ProductBatch removeQuantity(ProductBatch productBatch, Long quantity) {
        if(quantity <= 0 ){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        productBatch.quantity -= quantity

        if(productBatch.quantity < 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        return productBatchRepository.save(productBatch)
    }
}
