package br.com.mlm.dostock.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Version

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

    @OneToMany
    Set<Tag> tags

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    Set<ProductBatch> batches

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Product product = (Product) o

        if (id != product.id) return false
        return true
    }
}
