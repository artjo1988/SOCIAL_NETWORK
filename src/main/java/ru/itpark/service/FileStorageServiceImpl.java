package ru.itpark.service;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.fileStorageUtil.FileStorageUtil;
import ru.itpark.models.FileInfo;
import ru.itpark.models.User;
import ru.itpark.repositories.FileInfoRepository;
import ru.itpark.security.details.UserDetailsImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileStorageUtil fileStorageUtil;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Override
    public String saveFile(MultipartFile multipartFile, Authentication authentication) {
        FileInfo fileInfo = fileStorageUtil.converterFromMultipartfile(multipartFile);
        fileStorageUtil.saveToStorage(multipartFile, fileInfo.getStorageName());
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetailsImpl.getUser();
        if (user.getAvatarFileInfo() != null) {
            fileInfoRepository.updateFileInfoById(
                    fileInfo.getOriginalName(),
                    fileInfo.getStorageName(),
                    fileInfo.getSize(),
                    fileInfo.getType(),
                    fileInfo.getUrl(),
                    user.getAvatarFileInfo().getId());
            return fileInfo.getStorageName();
        } else {
            fileInfo.setOwner(user);
            fileInfoRepository.save(fileInfo);
            return fileInfo.getStorageName();
        }
    }


    @SneakyThrows
    @Override
    public void writeFileToResponse(String storageFileName, HttpServletResponse response){
        FileInfo fileInfo = fileInfoRepository.findOneByStorageName(storageFileName);
        response.setContentType(fileInfo.getType());
        InputStream inputStream = new FileInputStream(new File(fileInfo.getUrl()));
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream, outputStream);
        response.flushBuffer();
    }
}
