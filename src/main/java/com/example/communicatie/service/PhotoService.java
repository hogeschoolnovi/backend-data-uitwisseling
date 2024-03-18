package com.example.communicatie.service;

import com.example.communicatie.model.StudentPhoto;
import com.example.communicatie.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PhotoService {
    private final Path fileStoragePath;
    private final String fileStorageLocation;
    private final FileUploadRepository repo;

    public PhotoService(@Value("${my.upload_location}") String fileStorageLocation, FileUploadRepository repo) throws IOException{
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.repo = repo;

        Files.createDirectories(fileStoragePath);


    }

    public String storeFile(MultipartFile file) throws IOException{

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        repo.save(new StudentPhoto(fileName));
        return fileName;
    }

    public Resource downLoadFile(String fileName) {

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

}

