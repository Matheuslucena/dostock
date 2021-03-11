package br.com.mlm.dostock.init


import br.com.mlm.dostock.services.FolderService
import br.com.mlm.dostock.services.ProductFolderService
import br.com.mlm.dostock.services.ProductService
import br.com.mlm.dostock.services.TagService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Bootstrap implements CommandLineRunner{
    ProductService productService
    TagService tagService
    FolderService folderService
    ProductFolderService productFolderService

    Bootstrap(ProductService productService, TagService tagService, FolderService folderService, ProductFolderService productFolderService) {
        this.productService = productService
        this.tagService = tagService
        this.folderService = folderService
        this.productFolderService = productFolderService
    }

    @Override
    void run(String... args) throws Exception {
//        Folder f1 = new Folder([
//                name: "Computer"
//        ])
//        f1 = folderService.save(f1)
//        Product p1 = new Product([
//                name: "MacBook-Pro",
//                observation: "Later 2013",
//                code: "1234"
//        ])
//        p1.productFolders.add(new ProductFolder([folder: f1, product: p1]))
//        p1 = productService.save(p1)
    }
}
