package br.com.mlm.dostock.services

interface ProductService {

    List<br.com.mlm.dostock.domain.Product> list(Integer max, Integer offset, String sort, String order)

    br.com.mlm.dostock.domain.Product save(br.com.mlm.dostock.domain.Product product)

    br.com.mlm.dostock.domain.Product update(Long id, br.com.mlm.dostock.domain.Product product)

    void deleteById(Long id)

    void stockInput(br.com.mlm.dostock.domain.Product product, br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity, String observation)

    void stockOutput(br.com.mlm.dostock.domain.Product product, br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity, String observation)

}