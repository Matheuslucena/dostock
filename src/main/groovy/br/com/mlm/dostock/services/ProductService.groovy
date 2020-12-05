package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductBatch

interface ProductService {

    List<Product> list(Integer max, Integer offset, String sort, String order)

    Product save(Product product)

    Product update(Long id, Product product)

    void deleteById(Long id)

    void inventoryIncrease(Product product, ProductBatch productBatch, Long quantity, String observation)

    void inventoryDecrease(Product product, ProductBatch productBatch, Long quantity, String observation)

}