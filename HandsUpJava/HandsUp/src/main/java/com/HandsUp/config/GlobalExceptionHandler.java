package com.HandsUp.config;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle 404 Not Found
    @ExceptionHandler(value = { org.springframework.web.servlet.NoHandlerFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundError(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "PaginaDiErrore404"; // Assuming error404.html exists under src/main/resources/templates
    }
}

