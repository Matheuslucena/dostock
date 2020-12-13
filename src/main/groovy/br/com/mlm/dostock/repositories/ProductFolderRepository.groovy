package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductFolder
import org.springframework.data.repository.CrudRepository

interface ProductFolderRepository extends CrudRepository<ProductFolder, Long>{
    ProductFolder findByProductAndFolder(Product product, Folder folder)
}