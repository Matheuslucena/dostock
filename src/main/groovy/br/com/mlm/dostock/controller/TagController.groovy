package br.com.mlm.dostock.controller

import br.com.mlm.dostock.domain.Tag
import br.com.mlm.dostock.services.TagService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/tag")
@RestController
class TagController {

    TagService tagService

    TagController(TagService tagService) {
        this.tagService = tagService
    }

    @GetMapping("/")
    List<Tag> index(){
        return tagService.list()
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void delete(@PathVariable Long id){
        tagService.deleteById(id)
    }
}
