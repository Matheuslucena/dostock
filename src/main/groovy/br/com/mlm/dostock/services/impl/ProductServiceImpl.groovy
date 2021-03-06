package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Folder
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
    ProductFolderService productFolderService
    FileService fileService

    ProductServiceImpl(ProductRepository productRepository, ProductBatchService productBatchService,
                       ProductLogService productLogService, TagService tagService, ProductFolderService productFolderService) {
        this.productRepository = productRepository
        this.productBatchService = productBatchService
        this.productLogService = productLogService
        this.tagService = tagService
        this.productFolderService = productFolderService
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
    Product findById(Long id) {
        return productRepository.findById(id).orElse(null)
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
        return this.save(productToSave)
    }

    @Override
    void deleteById(Long id) {
        productRepository.deleteById(id)
    }

    @Override
    void inventoryIncrease(Product product, Folder folder, ProductBatch productBatch, BigDecimal quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(product.batchRequired && !productBatch){
            throw new Exception("Lote não informado")
        }

        if(productBatch){
            productBatchService.addQuantity(productBatch, quantity)
        }

        if(folder){
            productFolderService.addQuantity(product, folder, new BigDecimal(quantity))
        }

        productLogService.register(product, folder, productBatch, quantity, observation, ProductLogType.INCREASE)
        product.quantity += quantity

        productRepository.save(product)
    }

    @Transactional
    @Override
    void inventoryDecrease(Product product, Folder folder, ProductBatch productBatch, BigDecimal quantity, String observation) {
        if(quantity <= 0){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        if(product.batchRequired && !productBatch){
            throw new Exception("Lote não informado")
        }

        if(productBatch){
            productBatchService.removeQuantity(productBatch, quantity)
        }

        if(folder){
            productFolderService.removeQuantity(product, folder, new BigDecimal(quantity))
        }

        productLogService.register(product, folder, productBatch, quantity, observation, ProductLogType.DECREASE)
        product.quantity -= quantity

        if(product.quantity < 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        productRepository.save(product)
    }

    @Override
    List<Product> search(String name, String code, Long tagId, Long folderId) {
        return productRepository.search(name, code, tagId, folderId)
    }
}
