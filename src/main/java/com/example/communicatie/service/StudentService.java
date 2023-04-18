package com.example.communicatie.service;

import com.example.communicatie.exception.RecordNotFoundException;
import com.example.communicatie.model.FileUploadResponse;
import com.example.communicatie.model.Student;
import com.example.communicatie.repository.FileUploadRepository;
import com.example.communicatie.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository repository;

    private final FileUploadRepository uploadRepository;

    public StudentService(StudentRepository repository, FileUploadRepository uploadRepository){
        this.repository = repository;
        this.uploadRepository = uploadRepository;
    }

    public List<Student> getStudents() {

        return repository.findAll();

    }

    public Student getStudent(Long studentNumber) {

        Optional<Student> student = repository.findById(studentNumber);

        if(student.isPresent()) {

            return student.get();

        } else {

            throw new RecordNotFoundException("Student does not exist");

        }

    }

    public Student saveStudent(Student student) {

        return repository.save(student);

    }

    public Student updateStudent(Long studentNumber, Student student) {

        Optional<Student> optionalStudent = repository.findById(studentNumber);

        if (optionalStudent.isPresent()) {

            Student old = optionalStudent.get();
            if(student.getStudentNumber() != null){
                old.setStudentNumber(studentNumber);
            }
            if(student.getEmailAddress() != null){
                old.setEmailAddress(student.getEmailAddress());
            }
            if(student.getCourse() != null){
                old.setCourse(student.getCourse());
            }
            if(student.getName() != null){
                old.setName(student.getName());
            }
            if(old.getFile() != null && student.getFile() != null){
                old.setFile(student.getFile());
            } else if (old.getFile() != null) {
                old.setFile(old.getFile());
            }

            return repository.save(old);

        } else {

            throw new RecordNotFoundException("Student does not exist");

        }

    }

    public void deleteStudent(Long studentNumber) {

        repository.deleteById(studentNumber);

    }

    public void assignPhotoToStudent(String name, Long studentNumber) {

        Optional<Student> optionalStudent = repository.findById(studentNumber);

        Optional<FileUploadResponse> fileUploadResponse = uploadRepository.findByFileName(name);

        if (optionalStudent.isPresent() && fileUploadResponse.isPresent()) {

            FileUploadResponse photo = fileUploadResponse.get();

            Student student = optionalStudent.get();

            student.setFile(photo);

            repository.save(student);

        }

    }

}
