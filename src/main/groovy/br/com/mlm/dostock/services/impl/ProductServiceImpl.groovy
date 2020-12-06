package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.domain.Tag
import br.com.mlm.dostock.repositories.ProductRepository
import br.com.mlm.dostock.services.ProductBatchService
import br.com.mlm.dostock.services.ProductLogService
import br.com.mlm.dostock.services.ProductService
import br.com.mlm.dostock.services.TagService
import br.com.mlm.dostock.util.types.ProductLogType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl implements ProductService{

    ProductRepository productRepository
    ProductBatchService productBatchService
    ProductLogService productLogService
    TagService tagService

    ProductServiceImpl(ProductRepository productRepository, ProductBatchService productBatchService, ProductLogService productLogService, TagService tagService) {
        this.productRepository = productRepository
        this.productBatchService = productBatchService
        this.productLogService = productLogService
        this.tagService = tagService
    }

    @Override
    List<Product> list(Integer max, Integer offset, String sort, String order) {
        Sort sortObj = Sort.by(sort)."${order=='desc'?'descending':'ascending'}"()
        Pageable pageRequest = PageRequest.of(offset, max, sortObj)
        return productRepository.findAll(pageRequest) as List<Product>
    }

    @Override
    Product save(Product product) {
        Set<Tag> newTags = product.tags.findAll { Tag tag -> !tag.id} as Set<Tag>
        if(newTags?.size()){
            tagService.saveAll(newTags)
        }
        return productRepository.save(product)
    }

    @Override
    Product update(Long id, Product product) {
        Product productSaved = productRepository.findById(id).orElse(null)
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
    void inventoryIncrease(Product product, ProductBatch productBatch, Integer quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(productBatch){
            productBatchService.addQuantity(productBatch, quantity)
        }
        productLogService.register(product, productBatch, quantity, observation, ProductLogType.INCREASE)
        product.quantity += quantity

        productRepository.save(product)
    }

    @Override
    void inventoryDecrease(Product product, ProductBatch productBatch, Integer quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(productBatch){
            productBatchService.addQuantity(productBatch, quantity)
        }
        productLogService.register(product, productBatch, quantity, observation, ProductLogType.DECREASE)
        product.quantity -= quantity

        if(product.quantity < 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        productRepository.save(product)
    }
}
