package br.com.mlm.dostock.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
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

    BigDecimal quantity = 0
    BigDecimal minimumLevel
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

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    Set<ProductFolder> productFolders = new HashSet<>()

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Product product = (Product) o

        if (id != product.id) return false
        return true
    }
}
