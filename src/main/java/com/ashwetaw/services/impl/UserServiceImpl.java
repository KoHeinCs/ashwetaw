package com.ashwetaw.services.impl;

import com.ashwetaw.config.security.SpringSecurityUser;
import com.ashwetaw.email.EmailService;
import com.ashwetaw.entities.User;
import com.ashwetaw.enums.Role;
import com.ashwetaw.exceptions.EmailExistException;
import com.ashwetaw.exceptions.EmailNotFoundException;
import com.ashwetaw.exceptions.SpringJWTException;
import com.ashwetaw.exceptions.UsernameExistException;
import com.ashwetaw.repositories.UserRepository;
import com.ashwetaw.services.LoginAttemptService;
import com.ashwetaw.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

import static com.ashwetaw.constant.MessageConstant.*;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final LoginAttemptService loginAttemptService;
    private final EmailService emailService;

    @Value("${register.default-password}")
    private String registerDefaultPassword;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByUsername(username);
        SpringSecurityUser springSecurityUser = null;

        if (user != null) {
            validateLoginAttempt(user);
            user.setLastLoginDateDisplay(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            springSecurityUser = new SpringSecurityUser(user);
        }
        return springSecurityUser;
    }


    @Override
    public User register(String firstName, String lastName, String username, String email) throws SpringJWTException {
        validateEmailOrUserNameAlreadyExists(username, email);
        User user = prepareNewUser(firstName, lastName, username, email, true, true);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        userRepository.save(user);
        emailService.sendNewPasswordEmail(firstName, email, registerDefaultPassword);
        return user;
    }


    @Override
    public User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNotLocked, boolean isActive) throws SpringJWTException {
        validateEmailOrUserNameAlreadyExists(username, email);
        User user = prepareNewUser(firstName, lastName, username, email, isNotLocked, isActive);
        user.setRole(getRoleEnumName(role).name());
        user.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String currentUsername, String newFirstName, String newLastName, String newUsername, String newEmail, String role, boolean isNotLocked, boolean isActive) throws SpringJWTException {

        User currentUser = findByUsername(currentUsername);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setUsername(newUsername);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNotLocked);
        currentUser.setRole(getRoleEnumName(role).name());
        currentUser.setAuthorities(getRoleEnumName(role).getAuthorities());
        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG + username));
        return user;
    }

    @Override
    public User findByEmail(String email) throws EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(EMAIL_NOT_FOUND_MSG + email);
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void resetPassword(String email) throws SpringJWTException {
        User user = findByEmail(email);
        user.setPassword(encodePassword(registerDefaultPassword));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(), user.getEmail(), registerDefaultPassword);
    }


    private Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }


    private String encodePassword(String password) {
        return encoder.encode(password);
    }

    private void validateEmailOrUserNameAlreadyExists(String username, String email) throws SpringJWTException {
        // check whether user name already exists
        User userByUsername = userRepository.findUserByUsername(username).orElse(null);
        if (userByUsername != null && userByUsername.getUsername().equalsIgnoreCase(username)) {
            throw new UsernameExistException(USER_ALREADY_EXIST_MSG);

        }
        // check whether email already exists
        User userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail != null && userByEmail.getEmail().equalsIgnoreCase(email)) {
            throw new EmailExistException(EMAIL_ALREADY_EXIST_MSG);
        }
    }

    private User prepareNewUser(String firstName, String lastName, String username, String email, boolean isNotLocked, boolean isActive) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setActive(isActive);
        user.setNotLocked(isNotLocked);
        user.setPassword(encodePassword(registerDefaultPassword));
        return user;
    }

    private void validateLoginAttempt(User user) {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.removeUserFromLoginAttemptCache(user.getUsername());
        }
    }


}
