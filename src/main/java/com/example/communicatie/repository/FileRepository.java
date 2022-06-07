package com.example.communicatie.repository;

import com.example.communicatie.model.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileUploadResponse, String> {
}
