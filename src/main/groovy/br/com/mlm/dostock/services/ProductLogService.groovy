package br.com.mlm.dostock.services

interface ProductLogService {

    List<br.com.mlm.dostock.domain.ProductLog> list(Integer max, Integer offset, String sort, String order)

    void register(br.com.mlm.dostock.domain.Product product, br.com.mlm.dostock.domain.ProductBatch productBatch, Long quantity, String observation, br.com.mlm.dostock.util.types.ProductLogType productLogType)

}