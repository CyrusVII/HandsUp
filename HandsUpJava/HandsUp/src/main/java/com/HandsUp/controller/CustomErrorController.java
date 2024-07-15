package com.HandsUp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public String error() {
        return "PaginaDiErrore404"; // Nome del template HTML che hai creato
    }

    public String getErrorPath() {
        return PATH;
    }
}
