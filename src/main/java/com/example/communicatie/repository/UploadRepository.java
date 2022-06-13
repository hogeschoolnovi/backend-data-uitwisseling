package com.example.communicatie.repository;

import com.example.communicatie.model.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UploadRepository extends JpaRepository<FileDocument, Long> {
    FileDocument findByFileName(String fileName);
}
