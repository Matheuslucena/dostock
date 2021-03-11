package br.com.mlm.dostock.domain

import javax.persistence.*

@Entity
class ProductBatch extends BaseEntity{

    @Column(nullable = false)
    String number

    BigDecimal quantity = 0

    @Temporal(TemporalType.TIMESTAMP)
    Date expirationDate

    @ManyToOne
    Product product
}
