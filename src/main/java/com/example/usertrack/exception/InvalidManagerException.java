package com.example.usertrack.exception;

import jakarta.persistence.criteria.CriteriaBuilder;

public class InvalidManagerException extends RuntimeException{

    public InvalidManagerException(String message){
        super(message);
    }
}
