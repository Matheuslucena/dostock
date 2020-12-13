package br.com.mlm.dostock.domain

import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE folder SET deleted=true WHERE id=?")
class Folder extends BaseEntity{

    @Column(unique = true)
    String name

    @OneToOne
    Folder parentFolder

    @OneToMany
    Set<ProductFolder> productFolders

    Boolean deleted = false
}
