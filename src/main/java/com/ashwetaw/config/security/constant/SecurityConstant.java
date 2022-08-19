package com.ashwetaw.config.security.constant;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class SecurityConstant {
    private SecurityConstant(){}
    public static final long EXPIRATION_TIME = 432000000; // 5 days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_ARRAYS_LLS = "Auth0";
    public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    private static final String[] SWAGGER_URLS = {"/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**"};
    private static final String[] FREE_URLS = {"/","/login","/user/image/**","/api/**","/*.js"};
    public static final String[] PUBLIC_URLS = ArrayUtils.addAll(SWAGGER_URLS, FREE_URLS);


}
