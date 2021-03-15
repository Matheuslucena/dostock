package br.com.mlm.dostock.util.types

enum FileBucket {
    PRODUCT("product")

    String description

    FileBucket(String description) {
        this.description = description
    }
}