package br.com.mlm.dostock.domain

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class ProductFolder extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product

    @ManyToOne
    @JoinColumn(name = "folder_id")
    Folder folder

    BigDecimal quantity
}
