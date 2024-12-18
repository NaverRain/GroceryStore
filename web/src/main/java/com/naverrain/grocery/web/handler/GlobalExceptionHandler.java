package com.naverrain.grocery.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "errorpage";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.UNAUTHORIZED.value());
        return "errorpage";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
        return "errorpage";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDenied(AccessDeniedException e, Model model){
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorCode", HttpStatus.FORBIDDEN.value());
        return "errorpage";
    }
}
