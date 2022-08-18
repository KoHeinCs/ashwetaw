package com.ashwetaw.services.impl;

import com.ashwetaw.dto.StudentDTO;
import com.ashwetaw.dto.mapper.StudentMapper;
import com.ashwetaw.email.EmailService;
import com.ashwetaw.entities.Student;
import com.ashwetaw.exceptions.*;
import com.ashwetaw.repositories.StudentRepository;
import com.ashwetaw.services.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.ashwetaw.constant.MessageConstant.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public Student addNewStudent(String name, String rollNo, String year, char section, String address, String email) throws SpringJWTException {
        validateEmailOrStudentNameAlreadyExists(rollNo, email);
        Student student = prepareNewStudent(name, rollNo, year, section, address, email);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student updateStudent(String currentRollNo, String newName, String newYear, char newSection, String newAddress, String newEmail) throws SpringJWTException {
        Student currentStudent = findByStudentRollNo(currentRollNo);
        currentStudent.setName(newName);
        currentStudent.setYear(newYear);
        currentStudent.setSection(newSection);
        currentStudent.setAddress(newAddress);
        currentStudent.setEmail(newEmail);
        studentRepository.save(currentStudent);
        return currentStudent;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student findByStudentRollNo(String rollNo) throws StudentNotFoundException {
        Student Student = studentRepository.findStudentByRollNo(rollNo).orElseThrow(() -> new StudentNotFoundException(STUDENT_NOT_FOUND_MSG + rollNo));
        return Student;
    }

    @Override
    public Student findByEmail(String email) throws EmailNotFoundException {
        Student Student = studentRepository.findStudentByEmail(email);
        if (Student == null) {
            throw new EmailNotFoundException(EMAIL_NOT_FOUND_MSG + email);
        }
        return Student;
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student addNewStudent(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        return studentRepository.saveAndFlush(student);
    }

    private void validateEmailOrStudentNameAlreadyExists(String rollNo, String email) throws SpringJWTException {
        // check whether Student name already exists
        Student student = studentRepository.findStudentByRollNo(rollNo).orElse(null);
        if (Objects.nonNull(student) && student.getRollNo().equalsIgnoreCase(rollNo)) {
            throw new StudentExistException(STUDENT_ALREADY_EXIST_MSG);

        }
        // check whether email already exists
        Student StudentByEmail = studentRepository.findStudentByEmail(email);
        if (StudentByEmail != null && StudentByEmail.getEmail().equalsIgnoreCase(email)) {
            throw new EmailExistException(EMAIL_ALREADY_EXIST_MSG);
        }
    }

    private Student prepareNewStudent(String name, String rollNo, String year, char saction, String address, String email) {
        Student student = new Student();
        student.setName(name);
        student.setRollNo(rollNo);
        student.setYear(year);
        student.setSection(saction);
        student.setAddress(address);
        student.setEmail(email);
        return student;
    }



}
