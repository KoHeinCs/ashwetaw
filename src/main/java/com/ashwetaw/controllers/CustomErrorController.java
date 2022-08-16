package com.ashwetaw.controllers;

import com.ashwetaw.exceptions.CustomWhitelabelErrorPageException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) throws CustomWhitelabelErrorPageException {
        throw new CustomWhitelabelErrorPageException("There is no mapping for this url");
    }
}
