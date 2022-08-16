package com.ashwetaw.exceptions;

/**
 * @author Hein Htet Aung
 * @created at 16/08/2022
 **/
public class StudentExistException extends SpringJWTException{
    public StudentExistException(String s) {
        super(s);
    }
}
