package br.com.mlm.dostock.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE folder SET deleted=true WHERE id=?")
class Folder extends BaseEntity{

    @Column(unique = true)
    String name

    @OneToOne
    Folder parentFolder

    @JsonBackReference
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
    Set<ProductFolder> productFolders = new HashSet<>()

    Boolean deleted = false
}
