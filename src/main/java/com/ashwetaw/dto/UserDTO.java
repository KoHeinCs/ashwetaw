package com.ashwetaw.dto;


import com.ashwetaw.config.customannotation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Name cannot be empty")
    private String username;
    private String password;
    @ValidEmail(message = "Please provide a valid email address")
    private String email;
}
