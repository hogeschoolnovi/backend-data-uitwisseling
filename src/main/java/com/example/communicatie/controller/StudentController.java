package com.example.communicatie.controller;

import com.example.communicatie.model.Student;
import com.example.communicatie.service.PhotoService;
import com.example.communicatie.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final PhotoService photoService;


    public StudentController(StudentService studentService, PhotoService photoService) {
        this.studentService = studentService;
        this.photoService = photoService;
    }

    @GetMapping
    @Transactional
    public List<Student> getStudents() {

        List<Student> students;


        students = studentService.getStudents();


        return students;

    }

    @GetMapping("/{id}")
    @Transactional
    public Student getStudent(@PathVariable("id") Long studentNumber) {

        return studentService.getStudent(studentNumber);

    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {

        return studentService.saveStudent(student);

    }

    @PutMapping("/{studentNumber}")
    public Student updateStudent(@PathVariable Long studentNumber, @RequestBody Student student) {

        return studentService.updateStudent(studentNumber, student);

    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long studentNumber) {

        studentService.deleteStudent(studentNumber);

    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<Student> assignPhotoToStudent(@PathVariable("id") Long studentNumber,
                                               @RequestBody MultipartFile file) {

//        StudentPhoto photo = controller.singleFileUpload(file);

        // next line makes url. example "http://localhost:8080/download/naam.jpg"
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String fileName = photoService.storeFile(file, url);

        return ResponseEntity.created(URI.create(url)).body(studentService.assignPhotoToStudent(fileName, studentNumber));

    }
}
