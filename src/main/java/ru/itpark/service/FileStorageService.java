package ru.itpark.service;



import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


public interface FileStorageService {
    String saveFile(MultipartFile multipartFile, Authentication authentication);
    void writeFileToResponse(String fileName, HttpServletResponse response);
}