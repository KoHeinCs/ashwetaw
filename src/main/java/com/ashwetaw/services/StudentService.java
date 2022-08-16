package com.ashwetaw.services;

import com.ashwetaw.entities.Student;
import com.ashwetaw.exceptions.EmailNotFoundException;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.exceptions.StudentNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface StudentService {

    

    List<Student> getAllStudents();

    Student findByStudentRollNo(String rollNo) throws StudentNotFoundException;

    Student findByEmail(String email) throws EmailNotFoundException;

    Student addNewStudent(String name, String rollNo, String year,char section,String address,String email) throws SpringJWTException;

    Student updateStudent(String currentRollNo, String newName, String newYear,char newSection,String newAddress,String newEmail) throws SpringJWTException;

    void deleteStudent(long id);
}
