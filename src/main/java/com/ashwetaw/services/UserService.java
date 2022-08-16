package com.ashwetaw.services;

import com.ashwetaw.entities.User;
import com.ashwetaw.exceptions.EmailNotFoundException;
import com.ashwetaw.exceptions.SpringJWTException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String username, String email) throws SpringJWTException;

    List<User> getAllUsers();

    User findByUsername(String username) throws UsernameNotFoundException;

    User findByEmail(String email) throws EmailNotFoundException;

    User addNewUser(String firstName, String lastName, String username, String email,
                    String role, boolean isNotLocked, boolean isActive) throws SpringJWTException;

    User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail,
                    String role, boolean isNotLocked, boolean isActive) throws SpringJWTException;

    void deleteUser(long id);

    void resetPassword(String email) throws SpringJWTException;
}
