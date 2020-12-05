package br.com.mlm.dostock.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Version

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "update product set deleted=true where id=?")
class Product extends BaseEntity{

    @Version
    Integer version

    @Column(nullable = false)
    String name

    @Column(unique = true)
    String code

    Long quantity
    Long minimumLevel
    Boolean batchRequired = false

    @Column(columnDefinition = 'text')
    String observation

    @CreationTimestamp
    @Column(updatable = false)
    Date dateCreated

    @UpdateTimestamp
    @Column(nullable = false)
    Date lastUpdated

    Boolean deleted = false

}
