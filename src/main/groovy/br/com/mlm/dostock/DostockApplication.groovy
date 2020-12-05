package br.com.mlm.dostock

import org.modelmapper.ModelMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DostockApplication {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper()
    }

    static void main(String[] args) {
        SpringApplication.run(DostockApplication, args)
    }

}
