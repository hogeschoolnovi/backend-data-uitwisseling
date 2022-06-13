package com.example.communicatie.service;

import com.example.communicatie.exception.RecordNotFoundException;
import com.example.communicatie.model.FileDocument;
import com.example.communicatie.model.FileUploadResponse;
import com.example.communicatie.model.Student;
import com.example.communicatie.repository.FileUploadRepository;
import com.example.communicatie.repository.StudentRepository;
import com.example.communicatie.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository repository;
    private final UploadRepository photoRepository;

    private final FileUploadRepository uploadRepository;
    @Autowired
    public StudentService(StudentRepository repository, UploadRepository photoRepository, FileUploadRepository uploadRepository){
        this.repository = repository;
        this.photoRepository = photoRepository;
        this.uploadRepository = uploadRepository;
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

    public void assignPhotoToStudent(String name, String email) {

        Optional<Student> optionalStudent = repository.findById(email);

        if (optionalStudent.isPresent()) {

            FileDocument photo = photoRepository.findByFileName(name);

            Student student = optionalStudent.get();

            student.setFile(photo);

            repository.save(student);

        }

    }

}
