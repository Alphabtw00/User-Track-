package com.example.usertrack.controlleradvice;

import com.example.usertrack.exception.BulkUpdateNotAllowedException;
import com.example.usertrack.exception.InvalidIdException;
import com.example.usertrack.exception.InvalidManagerException;
import com.example.usertrack.exception.InvalidUpdateFieldsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserControllerAdvice {


    @ExceptionHandler(InvalidManagerException.class)
    public ResponseEntity<String> handleInvalidManagerException(InvalidManagerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage); //add validation errors ocured in one or more fields
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<String> handleInvalidIdException(InvalidIdException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(BulkUpdateNotAllowedException.class)
    public ResponseEntity<?> handleBulkUpdateNotAllowedException(BulkUpdateNotAllowedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("extraFields", ex.getExtraFields());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidUpdateFieldsException.class)
    public ResponseEntity<String> handleInvalidUpdateFieldsException(InvalidUpdateFieldsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }


}
