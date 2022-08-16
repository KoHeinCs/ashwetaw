package com.ashwetaw.exceptions;

public class StudentNotFoundException extends SpringJWTException {
    public StudentNotFoundException(String s) {
        super(s);
    }
}
