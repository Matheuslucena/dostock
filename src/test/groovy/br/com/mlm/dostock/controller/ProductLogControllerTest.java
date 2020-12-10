package br.com.mlm.dostock.controller;

import br.com.mlm.dostock.domain.Product;
import br.com.mlm.dostock.dto.ProductLogSearchDTO;
import br.com.mlm.dostock.repositories.ProductLogRepository;
import br.com.mlm.dostock.services.ProductLogService;
import br.com.mlm.dostock.util.types.ProductLogType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductLogController.class)
class ProductLogControllerTest {

    @MockBean
    ProductLogService productLogService;

    @MockBean
    ProductLogRepository productLogRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void searchLog() throws Exception {
        Product product = new Product();
        product.setId(1L);
        ProductLogSearchDTO searchDTO = new ProductLogSearchDTO();
        searchDTO.setDateInitial(new Date());
        searchDTO.setDateFinal(new Date());
        searchDTO.setLogType(ProductLogType.INCREASE);
        searchDTO.setProduct(product);

        given(productLogService.search(new Date(), new Date(), product, ProductLogType.INCREASE)).willReturn(new ArrayList<>());

        mockMvc.perform(post("/api/v1/product/log/search")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(searchDTO)))
                .andExpect(status().isOk());

        then(productLogService).should(times(1)).search(
                searchDTO.getDateInitial(), searchDTO.getDateFinal(), product, searchDTO.getLogType());
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}