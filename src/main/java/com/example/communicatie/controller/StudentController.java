package com.example.communicatie.controller;

import com.example.communicatie.model.Student;
import com.example.communicatie.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {this.service = service;}

    @GetMapping
    public List<Student> getStudents(@RequestParam(value = "name", required = false, defaultValue = "") String name) {

        List<Student> students;

        if (name == null) {

            students = service.getStudents();

        } else {

            students = service.findStudentsByName(name);

        }


        return students;

    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") String id) {

        var student = service.getStudent(id);

        return student;

    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {

        var st = service.saveStudent(student);

        return student;

    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable String id, @RequestBody Student student) {

        service.updateStudent(id, student);

        return student;

    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") String id) {

        service.deleteStudent(id);

    }

    @PostMapping("/{id}/userdata")
    public void assignPhotoToStudent(@PathVariable("id") String id,
                                     @RequestBody Long photoId) {

        service.assignPhotoToStudent(photoId, id);

    }
}
