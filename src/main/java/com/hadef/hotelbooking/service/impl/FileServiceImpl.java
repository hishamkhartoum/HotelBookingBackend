package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        if(file == null ||file.isEmpty()){
            throw new IllegalArgumentException("File is empty");
        }
        String fileName = Paths.get(Objects.requireNonNull(file.getOriginalFilename())).getFileName().toString();
        Path fullPath = Paths.get(path).resolve(fileName);

        Files.createDirectories(fullPath.getParent());
        Files.copy(file.getInputStream(),fullPath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceImage(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }

    @Override
    public void deleteImage(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        File file = new File(filePath);
        if(file.exists()){
            boolean isDeleted = file.delete();
            if(!isDeleted){
                throw new RuntimeException("Failed to delete file: " + fileName);
            }
        }else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    @Override
    public String updateImage(String path, MultipartFile file, String image) throws IOException {
        deleteImage(path,image);
        return uploadImage(path, file);
    }
}
