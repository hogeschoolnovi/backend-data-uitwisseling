package com.example.communicatie.controller;

import com.example.communicatie.model.FileUploadResponse;
import com.example.communicatie.model.Student;
import com.example.communicatie.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;
    private final PhotoController controller;


    public StudentController(StudentService service, PhotoController controller) {
        this.service = service;
        this.controller = controller;
    }

    @GetMapping
    @Transactional
    public List<Student> getStudents() {

        List<Student> students;


        students = service.getStudents();


        return students;

    }

    @GetMapping("/{id}")
    @Transactional
    public Student getStudent(@PathVariable("id") Long studentNumber) {

        return service.getStudent(studentNumber);

    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {

        return service.saveStudent(student);

    }

    @PutMapping("/{studentNumber}")
    public Student updateStudent(@PathVariable Long studentNumber, @RequestBody Student student) {

        return service.updateStudent(studentNumber, student);

    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long studentNumber) {

        service.deleteStudent(studentNumber);

    }

    @PostMapping("/{id}/photo")
    public void assignPhotoToStudent(@PathVariable("id") Long studentNumber,
                                     @RequestBody MultipartFile file) {

        FileUploadResponse photo = controller.singleFileUpload(file);

        service.assignPhotoToStudent(photo.getFileName(), studentNumber);

    }
}
