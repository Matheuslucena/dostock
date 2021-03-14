package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductFolder
import br.com.mlm.dostock.repositories.ProductFolderRepository
import br.com.mlm.dostock.services.ProductFolderService
import org.springframework.stereotype.Service

@Service
class ProductFolderServiceImpl implements ProductFolderService{

    ProductFolderRepository productFolderRepository

    ProductFolderServiceImpl(ProductFolderRepository productFolderRepository) {
        this.productFolderRepository = productFolderRepository
    }

    @Override
    void addQuantity(Product product, Folder folder, BigDecimal quantity) {
        if(quantity <= 0 ){
            throw new Exception("Quantidade deve ser maior que 0")
        }

        ProductFolder productFolder = productFolderRepository.findByProductAndFolder(product, folder)
        if(!productFolder){
            productFolder = new ProductFolder([product: product, folder: folder])
        }

        if(productFolder.quantity == null){
            productFolder.quantity = 0
        }
        productFolder.quantity += quantity

        productFolderRepository.save(productFolder)
    }

    @Override
    void removeQuantity(Product product, Folder folder, BigDecimal quantity) {
        ProductFolder productFolder = productFolderRepository.findByProductAndFolder(product, folder)
        if(!productFolder || productFolder.quantity <= 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        if(productFolder.quantity == null){
            productFolder.quantity = 0
        }
        productFolder.quantity -= quantity

        if(productFolder.quantity < 0){
            throw new Exception("Produto com estoque insuficiente")
        }

        productFolderRepository.save(productFolder)
    }
}
