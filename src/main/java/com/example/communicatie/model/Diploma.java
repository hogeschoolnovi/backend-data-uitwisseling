package com.example.communicatie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Diploma {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String url;
    private String contentType;

    /* Om een bestand of foto op te slaan in de database, heb je de @Lob (Large Object) annotatie nodig.
       Deze annotatie zorgt er voor dat er genoeg ruimte wordt gereserveerd in de database
       voor een groot object.
    */
    @Lob
    private byte[] contents;

    public Diploma(String title, String contentType, String url, byte[] contents) {
        this.url = url;
        this.title = title;
        this.contentType = contentType;
        this.contents = contents;
    }

    public Diploma() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
}
