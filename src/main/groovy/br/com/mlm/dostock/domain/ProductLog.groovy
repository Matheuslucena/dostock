package br.com.mlm.dostock.domain


import br.com.mlm.dostock.util.types.ProductLogType
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.OneToOne

@Entity
class ProductLog extends BaseEntity{

    @OneToOne
    Product product

    @OneToOne
    ProductBatch productBatch

    @Column(nullable = false)
    Long quantity

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ProductLogType logType

    @CreationTimestamp
    @Column(updatable = false)
    Date dateCreated

    @Column(columnDefinition = 'text')
    String observation
}
