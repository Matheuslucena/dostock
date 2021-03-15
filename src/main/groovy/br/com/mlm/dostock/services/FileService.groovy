package br.com.mlm.dostock.services

import br.com.mlm.dostock.util.types.FileBucket

interface FileService {
    String saveFile(byte[] bytes, String originalFileName, String id, FileBucket bucket)
    File getFile(String filePath)
}