package br.com.mlm.dostock.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
class ProductBatch extends BaseEntity{

    @Column(nullable = false)
    String number

    Long quantity = 0

    @Temporal(TemporalType.TIMESTAMP)
    Date validate

    @OneToOne
    Product product
}
