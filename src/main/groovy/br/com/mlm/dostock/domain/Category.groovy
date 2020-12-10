package br.com.mlm.dostock.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class Category extends BaseEntity{

    @Column(unique = true)
    String name

    @OneToOne
    Category parentCategory
}
