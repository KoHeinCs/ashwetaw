package com.ashwetaw.config.security;

import com.ashwetaw.common.HttpResponse;
import com.ashwetaw.config.security.constant.SecurityConstant;
import com.ashwetaw.config.security.util.JWTTokenProvider;
import com.ashwetaw.dto.UserDTO;
import com.ashwetaw.dto.mapper.UserMapper;
import com.ashwetaw.entities.User;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api("Set of endpoints for Creating, Retrieving, Updating and Deleting of Users.")
public class AuthenticationController {
    private static final String EMAIL_SENT = "An email with a new password was sent to: ";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @PostMapping("/register")
    private ResponseEntity<UserDTO> register(@Valid @ApiParam("User information for a new user to be created. Will ignore id field !!") @RequestBody UserDTO userDTO) throws SpringJWTException {
        User newUser = userService.register(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail());
        return new ResponseEntity<>(userMapper.toDTO(newUser), HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<UserDTO> login(@Valid @ApiParam("User information to login . Will only need username && password fields !!") @RequestBody UserDTO userDTO) throws UsernameNotFoundException {
        authenticate(userDTO.getUsername(), userDTO.getPassword());
        User loginUser = userService.findByUsername(userDTO.getUsername());
        SpringSecurityUser springSecurityUser = new SpringSecurityUser(loginUser);
        // put jwt token into header
        HttpHeaders jwtHeader = getJwtHeader(springSecurityUser);

        return new ResponseEntity<UserDTO>(userMapper.toDTO(loginUser), jwtHeader, HttpStatus.OK);
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email,@PathVariable("newPassword") String newPassword) throws SpringJWTException {
        userService.resetPassword(email,newPassword);
        return response(HttpStatus.OK, EMAIL_SENT + email);
    }


    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(SpringSecurityUser springSecurityUser) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(springSecurityUser));
        return httpHeaders;
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<HttpResponse>(body, httpStatus);
    }

}
