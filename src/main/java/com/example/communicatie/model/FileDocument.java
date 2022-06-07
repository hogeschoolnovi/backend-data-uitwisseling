package com.example.communicatie.model;

import javax.persistence.*;

@Entity
public class FileDocument {

    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @Lob
    private byte[] docFile;

    @OneToOne(mappedBy = "fileDocument")
    Student student;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}
