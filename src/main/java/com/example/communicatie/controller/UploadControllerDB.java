package com.example.communicatie.controller;

import com.example.communicatie.model.FileDocument;
import com.example.communicatie.model.FileUploadResponse;
import com.example.communicatie.service.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;



@CrossOrigin
@RestController
public class UploadControllerDB {
    private final UploadService service;

    public UploadControllerDB(UploadService service) {
        this.service = service;
    }

    @PostMapping("single/uploadDb")
    FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {


        // next line makes url. example "http://localhost:8080/download/naam.jpg"
        FileDocument fileDocument = service.uploadFileDocument(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        return new FileUploadResponse(fileDocument.getFileName(), url, contentType );
    }

    //    get for single download
    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        return service.singleFileDownload(fileName, request);
    }

    @GetMapping("/getAll/db")
    public Collection<FileDocument> getAllFromDB(){
        return service.getALlFromDB();
    }
}