package com.ashwetaw.config.security;

import com.ashwetaw.common.HttpResponse;
import com.ashwetaw.config.security.constant.SecurityConstant;
import com.ashwetaw.config.security.util.JWTTokenProvider;
import com.ashwetaw.entities.User;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private static final String EMAIL_SENT = "An email with a new password was sent to: ";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    private ResponseEntity<User> register(@RequestBody User user) throws SpringJWTException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<User>(newUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<User> login(@RequestBody User user) throws UsernameNotFoundException {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findByUsername(user.getUsername());
        SpringSecurityUser springSecurityUser = new SpringSecurityUser(loginUser);
        // put jwt token into header
        HttpHeaders jwtHeader = getJwtHeader(springSecurityUser);

        return new ResponseEntity<User>(loginUser, jwtHeader, HttpStatus.OK);
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws SpringJWTException {
        userService.resetPassword(email);
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
