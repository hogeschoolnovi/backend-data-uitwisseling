package com.example.communicatie.service;

import com.example.communicatie.exception.RecordNotFoundException;
import com.example.communicatie.model.Diploma;
import com.example.communicatie.repository.DiplomaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DiplomaService {

    private final DiplomaRepository diplomaRepository;

    public DiplomaService(DiplomaRepository diplomaRepository) {
        this.diplomaRepository = diplomaRepository;
    }

    public Diploma storeFile(MultipartFile file, String url) throws IOException {

        /*
        Wanneer je bestanden in de database opslaat, is het belangrijk om ook
        de "content type" en de "original filename" expliciet op te slaan.
        Deze informatie gaat anders namelijk verloren.
         */
        String originalFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        byte[] bytes = file.getBytes();

        Diploma diploma = new Diploma(originalFileName, contentType, url , bytes);

        return diplomaRepository.save(diploma);
    }

    public Diploma getDiplomaById(Long id){
        return diplomaRepository.findById(id).orElseThrow( () -> new RecordNotFoundException("Diploma with id " + id + " not found"));
    }
}
