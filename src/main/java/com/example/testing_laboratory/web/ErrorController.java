package com.example.testing_laboratory.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorController {
    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error";
    }
}
