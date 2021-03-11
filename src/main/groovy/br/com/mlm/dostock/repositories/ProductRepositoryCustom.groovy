package br.com.mlm.dostock.repositories

import br.com.mlm.dostock.domain.Product

interface ProductRepositoryCustom {
    List<Product> search(String name, String code, Long tagId, Long folderId)
}