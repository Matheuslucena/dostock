package br.com.mlm.dostock.services.impl

import br.com.mlm.dostock.services.FileService
import br.com.mlm.dostock.util.types.FileBucket
import org.apache.commons.io.FilenameUtils
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class FileServiceImpl implements FileService{

    Environment env

    FileServiceImpl(Environment env) {
        this.env = env
    }

    @Override
    String saveFile(byte[] bytes, String originalFileName, String id, FileBucket bucket) {
        String path = getPath(id, bucket, originalFileName)
        File newFile = new File(path)
        newFile << bytes
        return path
    }

    @Override
    File getFile(String filePath) {
        return new File(filePath)
    }

    private String getPath(String id, FileBucket bucket, String fileName){
        String separator = File.separator
        String dirPath = [env.getProperty("storage.path"), bucket.description, id].join(separator)
        File dir = new File(dirPath)
        if(!dir.exists()){
            dir.mkdirs()
        }
        return [dirPath, normalizeFileName(fileName)+"."+FilenameUtils.getExtension(fileName)].join(separator)
    }

    private String normalizeFileName(String fileName) {
        return "${new Date().time}_${removeFileExtension(fileName, true).replaceAll("[^a-zA-Z0-9\\.\\-]", "_")}"
    }

    private String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename
        }

        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*\$")
        return filename.replaceAll(extPattern, "")
    }
}
