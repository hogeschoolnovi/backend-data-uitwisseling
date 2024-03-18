package com.example.communicatie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class StudentPhoto {


    /* Het opgeslagen bestand staat niet in deze klasse opgeslagen.
       Deze klasse heeft enkel een verwijzing naar de naam van het bestand.
       We weten waar het bestand staat opgeslagen,
       dus met de naam kunnen we naar het bestand verwijzen als "./uploads/{fileName}"
    */
    @Id
    private String fileName;

    public StudentPhoto(String fileName) {
        this.fileName = fileName;
    }

    public StudentPhoto() {
    }

    public String getFileName() {
        return fileName;
    }

}
