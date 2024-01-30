package com.example.communicatie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StudentPhoto {


    // Het opgeslagen bestand staat niet in deze klasse opgeslage. Deze klasse heeft enkel een verwijzing naar de naam van het bestand.
    // We weten waar het bestand staat opgeslagen, dus met de naam kunnen we naar het bestand verwijzen als "./uploads/{fileName}"
    @Id
    private String fileName;

    private String contentType;

    private String url;

    public StudentPhoto(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
    }

    public StudentPhoto() {
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
