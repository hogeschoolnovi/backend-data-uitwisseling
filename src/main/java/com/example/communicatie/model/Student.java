package com.example.communicatie.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Student {

    @Id
    private String emailAddress;

    private String name;
    private String course;

    @OneToOne
    FileUploadResponse file;

    public Student() {
    }

    public Student(String emailAdress, String name, String course) {
        this.emailAddress = emailAdress;
        this.name = name;
        this.course = course;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public FileUploadResponse getFile() {
        return file;
    }

    public void setEmailAddress(String emailAdress) {
        this.emailAddress = emailAdress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setFile(FileUploadResponse file) {
        this.file = file;
    }
}
