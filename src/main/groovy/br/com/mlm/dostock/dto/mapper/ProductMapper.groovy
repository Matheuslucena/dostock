package br.com.mlm.dostock.dto.mapper

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.dto.ProductDTO
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Component

@Component
class ProductMapper {

    ModelMapper modelMapper

    ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper
    }

    ProductDTO toDTO(Product product){
        return modelMapper.map(product, ProductDTO)
    }

    Product toDomain(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product)
    }
}
