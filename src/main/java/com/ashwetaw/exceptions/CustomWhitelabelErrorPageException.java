package com.ashwetaw.exceptions;

public class CustomWhitelabelErrorPageException extends SpringJWTException {
    public CustomWhitelabelErrorPageException(String s) {
        super(s);
    }
}
