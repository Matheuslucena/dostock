package br.com.mlm.dostock.controller;

import br.com.mlm.dostock.domain.Tag;
import br.com.mlm.dostock.services.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @MockBean
    TagService tagService;

    @Autowired
    MockMvc mockMvc;

    void tearDown() {
        reset(tagService);
    }

    @Test
    void index() throws Exception {
        List<Tag> tags = new ArrayList<>();
        given(tagService.list()).willReturn(tags);

        mockMvc.perform(get("/api/v1/tag/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        then(tagService).should(times(1)).list();
    }

    @Test
    void deleteTag() throws Exception {
        mockMvc.perform(delete("/api/v1/tag/{id}", 1))
                .andExpect(status().isOk());
    }
}