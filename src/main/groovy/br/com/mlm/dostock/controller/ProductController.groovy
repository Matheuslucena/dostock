package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch
import br.com.mlm.dostock.dto.InventoryDTO
import br.com.mlm.dostock.dto.ProductDTO
import br.com.mlm.dostock.dto.mapper.ProductMapper
import br.com.mlm.dostock.repositories.FolderRepository
import br.com.mlm.dostock.repositories.ProductBatchRepository
import br.com.mlm.dostock.repositories.ProductRepository
import br.com.mlm.dostock.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RequestMapping("/api/v1/product")
@RestController
class ProductController {

    ProductService productService

    ProductRepository productRepository

    ProductBatchRepository productBatchRepository

    ProductMapper productMapper

    FolderRepository folderRepository

    ProductController(ProductService productService, ProductRepository productRepository,
                      ProductBatchRepository productBatchRepository, ProductMapper productMapper, FolderRepository folderRepository) {
        this.productService = productService
        this.productRepository = productRepository
        this.productBatchRepository = productBatchRepository
        this.productMapper = productMapper
        this.folderRepository = folderRepository
    }

    @GetMapping("/")
    List<Product> index(@RequestParam(defaultValue = "15") Integer max, @RequestParam(defaultValue = "0") Integer offset,
                        @RequestParam(defaultValue = "name") String sort, @RequestParam(defaultValue = "asc") String order){
        return productService.list(max, offset, sort, order)
    }

    @GetMapping("/{id}")
    Product edit(@PathVariable Long id){
        Product product = productService.findById(id)
        return product
    }

    @PostMapping("/")
    Product save(@Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toDomain(productDTO)
        return productService.save(product)
    }

    @PutMapping("/{id}")
    Product update(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toDomain(productDTO)
        return productService.update(id, product)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id) {
        productService.deleteById(id)
    }

    @PostMapping("/{id}/increase")
    @ResponseStatus(HttpStatus.OK)
    void inventoryIncrease(@PathVariable Long id, @Valid @RequestBody InventoryDTO inventoryDTO){
        Product product = productRepository.findById(id).orElse(null)
        ProductBatch productBatch = null
        def productBatchTemp = inventoryDTO.productBatch
        if(productBatchTemp) {
            productBatch = productBatchTemp?.id ? productBatchRepository.findById(productBatchTemp?.id).orElse(null) : null
            if(!productBatch && (productBatchTemp?.number || productBatchTemp?.expirationDate)) {
                productBatch = new ProductBatch()
                productBatch.number = productBatchTemp?.number
                productBatch.expirationDate = productBatchTemp?.expirationDate
                productBatch.product = product
            }
        }
        Folder folder = null
        if(inventoryDTO.folder){
            folder = folderRepository.findById(inventoryDTO.folder.id).orElse(null)
        }
        productService.inventoryIncrease(product, folder, productBatch, inventoryDTO.quantity, inventoryDTO.observation)
    }

    @PostMapping("/{id}/decrease")
    @ResponseStatus(HttpStatus.OK)
    void inventoryDecrease(@PathVariable Long id, @Valid @RequestBody InventoryDTO inventoryDTO){
        Product product = productRepository.findById(id).orElse(null)
        ProductBatch productBatch = null
        if(inventoryDTO.productBatch){
            productBatch = productBatchRepository.findById(inventoryDTO.productBatch.id).orElse(null)
        }

        Folder folder = null
        if(inventoryDTO.folder){
            folder = folderRepository.findById(inventoryDTO.folder.id).orElse(null)
        }

        productService.inventoryDecrease(product, folder, productBatch, inventoryDTO.quantity, inventoryDTO.observation)
    }
}
