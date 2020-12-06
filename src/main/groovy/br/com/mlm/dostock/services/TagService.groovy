package br.com.mlm.dostock.services

import br.com.mlm.dostock.domain.Tag

interface TagService {
    Tag save(Tag tag)
    Set<Tag> saveAll(Set<Tag> tags)
    void deleteById(Long id)
}