package br.com.mlm.dostock.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where

import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE product SET deleted=true WHERE id=? and version=?")
class Product extends BaseEntity{

    @Version
    Integer version

    @Column(nullable = false)
    String name

    @Column(unique = true)
    String code

    Integer quantity = 0
    Integer minimumLevel
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

    @OneToMany(cascade = CascadeType.PERSIST)
    Set<Tag> tags

}
