package com.example.communicatie.service;

import com.example.communicatie.exception.RecordNotFoundException;
import com.example.communicatie.model.Student;
import com.example.communicatie.repository.DocFileDao;
import com.example.communicatie.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository repository;
    private final DocFileDao dao;

    @Autowired
    public StudentService(StudentRepository repository, DocFileDao dao){
        this.repository = repository;
        this.dao = dao;
    }

    public List<Student> getStudents() {

        return repository.findAll();

    }

    public List<Student> findStudentsByName(String name) {

        return repository.findByNameContainingIgnoreCase(name);

    }

    public Student getStudent(String email) {

        Optional<Student> student = repository.findById(email);

        if(student.isPresent()) {

            return student.get();

        } else {

            throw new RecordNotFoundException("Student does not exist");

        }

    }

    public Student saveStudent(Student student) {

        return repository.save(student);

    }

    public void updateStudent(String email, Student student) {

        Optional<Student> optionalStudent = repository.findById(email);

        if (optionalStudent.isPresent()) {

            repository.deleteById(email);

            repository.save(student);

        } else {

            throw new RecordNotFoundException("Student does not exist");

        }

    }

    public void deleteStudent(String email) {

        repository.deleteById(email);

    }

    public void assignPhotoToStudent(Long photoId, String email) {

        var optionalPhoto = dao.findById(photoId);

        var optionalStudent = repository.findById(email);

        if (optionalPhoto.isPresent() && optionalStudent.isPresent()) {

            var photo = optionalPhoto.get();

            var student = optionalStudent.get();

            student.setFileDocument(photo);

            repository.save(student);

        }

    }

}
