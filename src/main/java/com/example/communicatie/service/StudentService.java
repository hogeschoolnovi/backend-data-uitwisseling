package com.example.communicatie.service;

import com.example.communicatie.exception.RecordNotFoundException;
import com.example.communicatie.model.Diploma;
import com.example.communicatie.model.StudentPhoto;
import com.example.communicatie.model.Student;
import com.example.communicatie.repository.FileUploadRepository;
import com.example.communicatie.repository.StudentRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository repository;

    private final FileUploadRepository uploadRepository;

    private final PhotoService photoService;

    public StudentService(StudentRepository repository, FileUploadRepository uploadRepository, PhotoService photoService){
        this.repository = repository;
        this.uploadRepository = uploadRepository;
        this.photoService = photoService;
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
            if(old.getStudentPhoto() != null && student.getStudentPhoto() != null){
                old.setStudentPhoto(student.getStudentPhoto());
            } else if (old.getStudentPhoto() != null) {
                old.setStudentPhoto(old.getStudentPhoto());
            }

            return repository.save(old);

        } else {

            throw new RecordNotFoundException("Student does not exist");

        }

    }

    public void deleteStudent(Long studentNumber) {

        repository.deleteById(studentNumber);

    }

    public Resource getPhotoFromStudent(Long studentNumber){

        Optional<Student> optionalStudent = repository.findById(studentNumber);
        if(optionalStudent.isEmpty()){
            throw new RecordNotFoundException("Student with student number " + studentNumber + " not found.");
        }

        StudentPhoto photo = optionalStudent.get().getStudentPhoto();

        if(photo == null){
            throw new RecordNotFoundException("Student " + studentNumber + " had no photo.");
        }

        return photoService.downLoadFile(photo.getFileName());
    }


    public Student assignPhotoToStudent(String name, Long studentNumber) {

        Optional<Student> optionalStudent = repository.findById(studentNumber);

        Optional<StudentPhoto> optionalPhoto = uploadRepository.findByFileName(name);

        if (optionalStudent.isPresent() && optionalPhoto.isPresent()) {

            StudentPhoto photo = optionalPhoto.get();

            Student student = optionalStudent.get();

            student.setStudentPhoto(photo);

            return repository.save(student);

        } else {
            throw new RecordNotFoundException("student of foto niet gevonden");
        }

    }

    public Student addDiploma(Long studentNumber, Diploma diploma) {
        Optional<Student> optionalStudent = repository.findById(studentNumber);
        if(optionalStudent.isEmpty()){
            throw new RecordNotFoundException("Student with student number " + studentNumber + " not found.");
        }
        Student student = optionalStudent.get();
        student.setDiploma(diploma);
        return repository.save(student);
    }

    public Diploma getDiplomaFromStudent(Long studentNumber) {
        Optional<Student> optionalStudent = repository.findById(studentNumber);
        if(optionalStudent.isEmpty()){
            throw new RecordNotFoundException("Student with student number " + studentNumber + " not found.");
        }
        return optionalStudent.get().getDiploma();
    }
}
