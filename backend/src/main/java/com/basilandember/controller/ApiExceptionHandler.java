package com.basilandember.controller;

import java.util.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class) ResponseEntity<?> status(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message",Objects.requireNonNullElse(ex.getReason(),"Request failed.")));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException ex) {
        Map<String,String> fields=new LinkedHashMap<>(); ex.getBindingResult().getFieldErrors().forEach(e -> fields.putIfAbsent(e.getField(),e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("message","Please check the highlighted fields.","fields",fields));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class) ResponseEntity<?> malformed() { return ResponseEntity.badRequest().body(Map.of("message","Request contains invalid or missing values.")); }
    @ExceptionHandler(DataAccessException.class) ResponseEntity<?> database() { return ResponseEntity.status(503).body(Map.of("message","The menu is temporarily unavailable. Please try again shortly.")); }
}
