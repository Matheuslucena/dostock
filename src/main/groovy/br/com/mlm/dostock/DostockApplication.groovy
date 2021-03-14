package br.com.mlm.dostock

import org.modelmapper.ModelMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class DostockApplication {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper()
    }

    static void main(String[] args) {
        SpringApplication.run(DostockApplication, args)
    }

    @Bean
    WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
            }
            @Override
            void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("forward:/index.html")
            }
        }
    }


}
