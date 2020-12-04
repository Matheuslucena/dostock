package br.com.mlm.dostock.services.impl


import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl implements br.com.mlm.dostock.services.ProductService{

    br.com.mlm.dostock.repositories.ProductRepository productRepository
    br.com.mlm.dostock.services.ProductBatchService productBatchService
    br.com.mlm.dostock.services.ProductLogService productLogService

    ProductServiceImpl(br.com.mlm.dostock.repositories.ProductRepository productRepository, br.com.mlm.dostock.services.ProductBatchService productBatchService,
                       br.com.mlm.dostock.services.ProductLogService productLogService) {
        this.productRepository = productRepository
        this.productBatchService = productBatchService
        this.productLogService = productLogService
    }

    @Override
    List<br.com.mlm.dostock.domain.Product> list(Integer max, Integer offset, String sort, String order) {
        Sort sortObj = Sort.by(sort)."${order=='desc'?'descending':'ascending'}"()
        Pageable pageRequest = PageRequest.of(offset, max, sortObj)
        return productRepository.findAll(pageRequest) as List<br.com.mlm.dostock.domain.Product>
    }

    @Override
    br.com.mlm.dostock.domain.Product save(br.com.mlm.dostock.domain.Product product) {
        return productRepository.save(product)
    }

    @Override
    br.com.mlm.dostock.domain.Product update(Long id, br.com.mlm.dostock.domain.Product product) {
        br.com.mlm.dostock.domain.Product productSaved = productRepository.findById(id).orElse(null)
        productSaved.name = product.name
        productSaved.code = product.code
        productSaved.minimumLevel = product.minimumLevel
        productSaved.batchRequired = product.batchRequired
        productSaved.observation = product.observation
        return productRepository.save(productSaved)
    }

    @Override
    void deleteById(Long id) {
        productRepository.deleteById(id)
    }

    @Override
    void stockInput(br.com.mlm.dostock.domain.Product product, br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(productBatch){
            productBatchService.addQuantity(productBatch, quantity)
        }
        productLogService.register(product, productBatch, quantity, observation, br.com.mlm.dostock.util.types.ProductLogType.INPUT)
        product.quantity += quantity

        productRepository.save(product)
    }

    @Override
    void stockOutput(br.com.mlm.dostock.domain.Product product, br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(productBatch){
            productBatchService.addQuantity(productBatch, quantity)
        }
        productLogService.register(product, productBatch, quantity, observation, br.com.mlm.dostock.util.types.ProductLogType.OUTPUT)
        product.quantity -= quantity

        if(product.quantity < 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        productRepository.save(product)
    }
}
