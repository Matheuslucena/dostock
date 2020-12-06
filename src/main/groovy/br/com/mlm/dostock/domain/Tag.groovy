package br.com.mlm.dostock.domain

import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Tag extends BaseEntity{
    @Column(unique = true)
    String name
}
