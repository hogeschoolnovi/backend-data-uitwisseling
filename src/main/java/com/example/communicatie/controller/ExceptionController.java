package com.example.communicatie.controller;

import com.example.communicatie.exception.ReadFileException;
import com.example.communicatie.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;

@RestController
@CrossOrigin
@ControllerAdvice
public class ExceptionController {

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<Void> exception(RecordNotFoundException exception) {

        logger.warn(exception.getMessage());
        return ResponseEntity.notFound().build();

    }

    @ExceptionHandler(value = ReadFileException.class)
    public ResponseEntity<String> exception(ReadFileException exception) {

        logger.warn(exception.getMessage());
        return ResponseEntity.internalServerError().body(exception.getMessage());

    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<String> exception(IOException exception){
        String message = "Problemen met file opslag";
        logger.warn(message);
        return ResponseEntity.internalServerError().body(message);

    }

}