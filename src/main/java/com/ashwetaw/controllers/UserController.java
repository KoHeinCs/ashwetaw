package com.ashwetaw.controllers;

import com.ashwetaw.common.HttpResponse;
import com.ashwetaw.entities.User;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private final UserService userService;

    @PostMapping("/add")
    private ResponseEntity<User> add(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("isActive") String isActive,
            @RequestParam("isNotLocked") String isNotLocked
    ) throws SpringJWTException {
        User newUser = userService.addNewUser(firstName, lastName, username, email,
                Boolean.parseBoolean(isNotLocked), Boolean.parseBoolean(isActive));
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<User> update(
            @RequestParam("currentUsername") String currentUsername,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("isActive") String isActive,
            @RequestParam("isNotLocked") String isNotLocked
    ) throws SpringJWTException {
        User updateUser = userService.updateUser(currentUsername, firstName, lastName, username, email,
                Boolean.parseBoolean(isNotLocked), Boolean.parseBoolean(isActive));
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") long id) throws SpringJWTException {
        userService.deleteUser(id);
        return response(HttpStatus.OK, USER_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<HttpResponse>(body, httpStatus);
    }


}
