package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch

interface ProductService {

    List<Product> list(Integer max, Integer offset, String sort, String order)

    Product save(Product product)

    Product update(Long id, Product product)

    void deleteById(Long id)

    void inventoryIncrease(Product product, Folder folder, ProductBatch productBatch, BigDecimal quantity, String observation)

    void inventoryDecrease(Product product, Folder folder, ProductBatch productBatch, BigDecimal quantity, String observation)

    List<Product> search(String name, String code, Long tagId, Long folderId)

}