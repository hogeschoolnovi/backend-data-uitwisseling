package com.example.communicatie.repository;

import com.example.communicatie.model.StudentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<StudentPhoto, String> {
    Optional<StudentPhoto> findByFileName(String fileName);
}
