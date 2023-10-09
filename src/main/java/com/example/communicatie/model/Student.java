package com.example.communicatie.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.*;

@Entity
public class Student {

    // Onderstaand is een "generated id" met een "sequence generator".
    // De generatie van een id gebruikt altijd een sequence generator achter de schermen, maar we kunnen ook dus ook handmatig instellen aan welke regels die generatie moet voldoen.
    // Het voornaamste nut van deze sequence generator is dat de start waarde van de id's begint bij 1003.
    // Zoals je ziet heeft de @SequenceGenerator een "naam" attribuut. Deze naam gebruiken we in de @GeneratedValue om de generator te kunnen gebruiken (dus niet de "sequence_name" parameter).
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
    StudentPhoto studentPhoto;

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

    public StudentPhoto getFile() {
        return studentPhoto;
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

    public void setFile(StudentPhoto studentPhoto) {
        this.studentPhoto = studentPhoto;
    }
}
