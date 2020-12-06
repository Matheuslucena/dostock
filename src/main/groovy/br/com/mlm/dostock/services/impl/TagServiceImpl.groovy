package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.domain.Tag
import br.com.mlm.dostock.repositories.TagRepository
import br.com.mlm.dostock.services.TagService
import org.springframework.stereotype.Service

@Service
class TagServiceImpl implements TagService{
    TagRepository tagRepository

    TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository
    }

    @Override
    Tag save(Tag tag) {
        return tagRepository.save(tag)
    }

    @Override
    Set<Tag> saveAll(Set<Tag> tags) {
        return tagRepository.saveAll(tags) as Set<Tag>
    }

    @Override
    void deleteById(Long id) {
        tagRepository.deleteById(id)
    }
}
