package ru.itpark.fileStorageUtil;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.models.FileInfo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
@Getter
@Setter
public class FileStorageUtil {

    @Value("${storage.path}")
    private String storagePath;

    private final List<String> typesImages = Arrays.asList("image/jpeg", "image/png");

    @SneakyThrows
    public void saveToStorage(MultipartFile multipartFile, String storageFileName){
        Files.copy(multipartFile.getInputStream(), Paths.get(getStoragePath(), storageFileName));
    }

    public FileInfo converterFromMultipartfile(MultipartFile multipartFile){
        String originalName = multipartFile.getOriginalFilename();
        String type = multipartFile.getContentType();
        long size = multipartFile.getSize();
        String storageFileNAme = createStorageFileName(originalName);
        String url = getUrlOfFile(storageFileNAme);
        return FileInfo.builder()
                    .originalName(originalName)
                    .storageName(storageFileNAme)
                    .size(size)
                    .type(type)
                    .url(url)
                    .build();
    }

    public String getUrlOfFile(String storageFileName){
        return storagePath + "/" + storageFileName;
    }

    public String createStorageFileName(String originalFileName){
        String extension = FilenameUtils.getExtension(originalFileName);
        String newFileName = UUID.randomUUID().toString();
        return newFileName + "." + extension;
    }

    public void validImage(MultipartFile multipartFile){
        if(!typesImages.contains(multipartFile.getContentType())){
            throw new IllegalArgumentException("Bad format file");
        }
        return;
    }

}
