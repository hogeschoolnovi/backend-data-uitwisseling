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
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

/*
    Let op: Dit voorbeeld dient ter illustratie van de upload/download en de samenwerking met de frontend.
            Voor de simpliciteit is hier geen gebruik gemaakt van DTO's of security
 */
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
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long studentNumber) {
        return ResponseEntity.ok(studentService.getStudent(studentNumber));
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/students/")
                .path(Objects.requireNonNull(savedStudent.getStudentNumber().toString()))
                .toUriString();
        return ResponseEntity.created(URI.create(url)).body(savedStudent);
    }

    @PutMapping("/{studentNumber}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentNumber, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(studentNumber, student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long studentNumber) {
        studentService.deleteStudent(studentNumber);
        return ResponseEntity.noContent().build();
    }


    // Zie dat de POST methode voor photo (filesystem) en diploma (database) bijna hetzelfde zijn.
    // Er wordt enkel een andere "storeFile" methode gebruikt.

    @PostMapping("/{id}/photo")
    public ResponseEntity<Student> addPhotoToStudent(@PathVariable("id") Long studentNumber,
                                                     @RequestBody MultipartFile file)
    throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/students/")
                .path(Objects.requireNonNull(studentNumber.toString()))
                .path("/photo")
                .toUriString();
        String fileName = photoService.storeFile(file);
        Student student = studentService.assignPhotoToStudent(fileName, studentNumber);

        return ResponseEntity.created(URI.create(url)).body(student);

    }

    @PostMapping("/{id}/diploma")
    public ResponseEntity<Student> addDiplomaToStudent(@PathVariable("id") Long studentNumber,
                                                       @RequestBody MultipartFile file) throws IOException{
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/students/")
                .path(Objects.requireNonNull(studentNumber.toString()))
                .path("/diploma")
                .toUriString();

        Diploma diploma = diplomaService.storeFile(file, url);

        Student student = studentService.addDiploma(studentNumber, diploma);

        return ResponseEntity.created(URI.create(url)).body(student);
    }

//    Zie dat ook de get methode voor photo (filesystem) en diploma (database) bijna hetzelfde zijn.
//    Enkel de returntype van de methode is anders.

    @GetMapping("/{id}/photo")
    public ResponseEntity<Resource> getStudentPhoto(@PathVariable("id") Long studentNumber, HttpServletRequest request){
        Resource resource = studentService.getPhotoFromStudent(studentNumber);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            /*
            "application/octet-steam" is de generieke mime type voor byte data.
            Het is beter om een specifiekere mimetype te gebruiken, zoals "image/jpeg".
            Mimetype is nodig om de frontend te laten weten welke soort data het is.
            Met de juiste MimeType en Content-Disposition, kun je de plaatjes of PDFs die je upload
            zelfs in de browser weergeven.
             */
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
