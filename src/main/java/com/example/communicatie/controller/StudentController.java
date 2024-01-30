package com.example.communicatie.controller;

import com.example.communicatie.model.Diploma;
import com.example.communicatie.model.Student;
import com.example.communicatie.service.DiplomaService;
import com.example.communicatie.service.PhotoService;
import com.example.communicatie.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final PhotoService photoService;
    private final DiplomaService diplomaService;


    public StudentController(StudentService studentService, PhotoService photoService, DiplomaService diplomaService) {
        this.studentService = studentService;
        this.photoService = photoService;
        this.diplomaService = diplomaService;
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


    // Zie dat de POST methode voor photo (filesystem) en dilpoma (database) bijna hetzelfde zijn. Er wordt enkel een andere "storeFile" methode gebruikt.

    @PostMapping("/{id}/photo")
    public ResponseEntity<Student> addPhotoToStudent(@PathVariable("id") Long studentNumber,
                                               @RequestBody MultipartFile file) {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/students/").path(Objects.requireNonNull(studentNumber.toString())).path("/photo").toUriString();

        String fileName = photoService.storeFile(file, url);

        Student student = studentService.assignPhotoToStudent(fileName, studentNumber);

        return ResponseEntity.created(URI.create(url)).body(student);

    }

    @PostMapping("/{id}/diploma")
    public ResponseEntity<Student> addDiplomaToStudent(@PathVariable("id") Long studentNumber,
                                                       @RequestBody MultipartFile file) throws IOException{
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/students/").path(Objects.requireNonNull(studentNumber.toString())).path("/diploma").toUriString();

        Diploma diploma = diplomaService.storeFile(file, url);

        Student student = studentService.addDiploma(studentNumber, diploma);

        return ResponseEntity.created(URI.create(url)).body(student);
    }

//    Zie dat ook de get methode voor photo (filesystem) en diploma (database) bijna hetzelfde zijn. Enkel de returntype van de methode is anders.

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getStudentPhoto(@PathVariable("id") Long studentNumber, HttpServletRequest request){
        Resource resource = studentService.getPhotoFromStudent(studentNumber);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }



    @GetMapping("/{id}/diploma")
    public ResponseEntity<byte[]> getStudentDiploma(@PathVariable("id") Long studentNumber){

        Diploma diploma = studentService.getDiplomaFromStudent(studentNumber);

        MediaType mediaType;

        try {
            mediaType = MediaType.parseMediaType(diploma.getContentType());
        } catch (InvalidMediaTypeException ignore){
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }



        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + diploma.getTitle())
                .body(diploma.getContents());
    }

}
