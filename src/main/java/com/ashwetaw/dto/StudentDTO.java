package com.ashwetaw.dto;

import com.ashwetaw.config.customannotation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Data
public class StudentDTO {
    private Long id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Roll No cannot be empty")
    private String rollNo;
    private String year;
    private char section;
    private String address;
    @ValidEmail(message = "Please provide a valid email address")
    private String email;
}
