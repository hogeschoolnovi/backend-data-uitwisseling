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

        Diploma diploma = new Diploma(file.getOriginalFilename(), file.getContentType(), url, file.getBytes());

        return diplomaRepository.save(diploma);
    }

    public Diploma getDiplomaById(Long id){
        return diplomaRepository.findById(id).orElseThrow( () -> new RecordNotFoundException("Diploma with id " + id + " not found"));
    }
}
