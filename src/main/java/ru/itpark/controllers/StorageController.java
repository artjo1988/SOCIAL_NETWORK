package ru.itpark.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.service.FileStorageService;

import javax.servlet.http.HttpServletResponse;


@Controller
public class StorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/files")
    public ResponseEntity<String> hundlerFileDownload(@RequestParam("file") MultipartFile multipartFile, Authentication authentication){
        String filePath = fileStorageService.saveFile(multipartFile, authentication);
        return ResponseEntity.ok().body(filePath);
    }

    @GetMapping("/files/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
        fileStorageService.writeFileToResponse(fileName, response);
    }

}
