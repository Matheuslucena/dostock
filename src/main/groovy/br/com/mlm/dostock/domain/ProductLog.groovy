package br.com.mlm.dostock.domain

import br.com.mlm.dostock.util.types.ProductLogType
import org.hibernate.annotations.CreationTimestamp

import javax.persistence.*

@Entity
class ProductLog extends BaseEntity{

    @OneToOne
    Product product

    @OneToOne
    ProductBatch productBatch

    @OneToOne
    Folder folder

    @Column(nullable = false)
    BigDecimal quantity

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    ProductLogType logType

    @CreationTimestamp
    @Column(updatable = false)
    Date dateCreated

    @Column(columnDefinition = 'text')
    String observation
}
