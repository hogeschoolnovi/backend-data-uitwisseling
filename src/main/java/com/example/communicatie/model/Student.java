package com.example.communicatie.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_sequence"),
                    @Parameter(name = "initial_value", value = "1003"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long studentNumber;
    
    private String emailAddress;
    private String name;
    private String course;

    @OneToOne
    FileUploadResponse file;

    public Student() {
    }
    public Student(String emailAddress, String name, String course) {
        this.emailAddress = emailAddress;
        this.name = name;
        this.course = course;
    }
    public Student(Long studentNumber, String emailAddress, String name, String course) {
        this.studentNumber = studentNumber;
        this.emailAddress = emailAddress;
        this.name = name;
        this.course = course;
    }

    public Long getStudentNumber() {
        return studentNumber;
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

    public void setStudentNumber(Long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
