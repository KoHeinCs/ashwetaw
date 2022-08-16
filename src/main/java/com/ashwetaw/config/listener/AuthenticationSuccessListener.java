package com.ashwetaw.config.listener;

import com.ashwetaw.config.security.SpringSecurityUser;
import com.ashwetaw.services.LoginAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessListener {
    private final LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event){
        Object principle = event.getAuthentication().getPrincipal();
        if (principle instanceof SpringSecurityUser){
            SpringSecurityUser springSecurityUser = (SpringSecurityUser) principle;
            loginAttemptService.removeUserFromLoginAttemptCache(springSecurityUser.getUsername());
        }

    }

}
