package com.ashwetaw.controllers;

import com.ashwetaw.dto.StudentDTO;
import com.ashwetaw.entities.Student;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Hein Htet Aung
 * @created at 17/08/2022
 **/
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    private ResponseEntity<Student> register(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.addNewStudent(studentDTO);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }
}
