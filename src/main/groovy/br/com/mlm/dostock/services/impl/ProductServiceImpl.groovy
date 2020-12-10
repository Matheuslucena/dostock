package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Category
import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.domain.Tag
import br.com.mlm.dostock.repositories.ProductRepository
import br.com.mlm.dostock.services.*
import br.com.mlm.dostock.util.types.ProductLogType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceImpl implements ProductService{

    ProductRepository productRepository
    ProductBatchService productBatchService
    ProductLogService productLogService
    TagService tagService
    CategoryService categoryService

    ProductServiceImpl(ProductRepository productRepository, ProductBatchService productBatchService, ProductLogService productLogService, TagService tagService, CategoryService categoryService) {
        this.productRepository = productRepository
        this.productBatchService = productBatchService
        this.productLogService = productLogService
        this.tagService = tagService
        this.categoryService = categoryService
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
        if(product.category && !product.category?.id){
            Category category = categoryService.save(product.category)
            product.category = category
        }
        return productRepository.save(product)
    }

    @Override
    Product update(Long id, Product product) {
        Product productToSave = productRepository.findById(id).orElse(null)
        productToSave.name = product.name
        productToSave.code = product.code
        productToSave.minimumLevel = product.minimumLevel
        productToSave.batchRequired = product.batchRequired
        productToSave.observation = product.observation
        productToSave.tags = product.tags
        if(product.category && !product.category?.id){
            Category category = categoryService.save(product.category)
            product.category = category
        }
        productToSave.category = product.category
        return this.save(productToSave)
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

        if(product.batchRequired && !productBatch){
            throw new Exception("Lote não informado")
        }

        if(productBatch){
            productBatchService.addQuantity(productBatch, quantity)
        }
        productLogService.register(product, productBatch, quantity, observation, ProductLogType.INCREASE)
        product.quantity += quantity

        productRepository.save(product)
    }

    @Transactional
    @Override
    void inventoryDecrease(Product product, ProductBatch productBatch, Integer quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(product.batchRequired && !productBatch){
            throw new Exception("Lote não informado")
        }

        if(productBatch){
            productBatchService.removeQuantity(productBatch, quantity)
        }
        productLogService.register(product, productBatch, quantity, observation, ProductLogType.DECREASE)
        product.quantity -= quantity

        if(product.quantity < 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        productRepository.save(product)
    }
}
