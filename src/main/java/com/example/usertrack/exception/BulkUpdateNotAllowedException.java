package com.example.usertrack.exception;

import lombok.Data;

import java.util.Set;

@Data
public class BulkUpdateNotAllowedException extends RuntimeException {
    private Set<String> extraFields;

    public BulkUpdateNotAllowedException(String message, Set<String> extraFields) {
        super(message);
        this.extraFields = extraFields;
    }

    public Set<String> getExtraFields() {
        return extraFields;
    }
}