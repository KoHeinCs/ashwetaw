package com.ashwetaw.config.listener;

import com.ashwetaw.services.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * Listen username and password login failure
 * add to catch when user login attempt failed
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener{
    private final LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event)  {
        Object principle = event.getAuthentication().getPrincipal();
        if (principle instanceof String){
            String username = (String) principle;
            loginAttemptService.addUserToLoginAttemptCache(username);
        }

    }

}
