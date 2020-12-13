package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Folder
import br.com.mlm.dostock.domain.Product

interface ProductFolderService {
    void addQuantity(Product product, Folder folder, BigDecimal quantity)
    void removeQuantity(Product product, Folder folder, BigDecimal quantity)
}