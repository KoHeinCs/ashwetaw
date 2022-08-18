package com.ashwetaw.controllers;

import com.ashwetaw.common.HttpResponse;
import com.ashwetaw.dto.StudentDTO;
import com.ashwetaw.entities.Student;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Hein Htet Aung
 * @created at 17/08/2022
 **/
@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    public static final String STUDENT_DELETED_SUCCESSFULLY = "Student deleted successfully";
    private final StudentService studentService;

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getAllStudents(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Student> studentList = studentService.getAllStudents(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<Student> addNewStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentService.addNewStudent(studentDTO);
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<Student> updateStudent(
            @RequestParam("currentRollNo") String currentRollNo,
            @RequestParam("newName") String newName,
            @RequestParam("newYear") String newYear,
            @RequestParam("newSection") char newSection,
            @RequestParam("newAddress") String newAddress,
            @RequestParam("newEmail") String newEmail
    ) throws SpringJWTException {
        Student updateStudent = studentService.updateStudent(currentRollNo, newName, newYear, newSection, newAddress, newEmail);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('user:delete')")
    public ResponseEntity<HttpResponse> deleteStudent(@PathVariable("id") long id) throws SpringJWTException{
        studentService.deleteStudent(id);
        return response(HttpStatus.OK, STUDENT_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<>(body, httpStatus);
    }


}
