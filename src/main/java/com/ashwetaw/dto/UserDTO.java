package com.ashwetaw.dto;


import com.ashwetaw.config.customannotation.ValidEmail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Name cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    @ValidEmail(message = "Please provide a valid email address")
    private String email;
}
