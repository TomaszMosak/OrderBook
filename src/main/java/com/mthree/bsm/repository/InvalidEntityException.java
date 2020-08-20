package com.mthree.bsm.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception to be thrown when an entity in the system is invalid according its rules. Contains a field {@link
 * #validationErrors} indicating all the errors with the entity.
 */
public class InvalidEntityException extends Exception {

    private List<String> validationErrors;

    public InvalidEntityException() {
        this.validationErrors = new ArrayList<>();
    }

    public InvalidEntityException(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public InvalidEntityException(String message, List<String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public InvalidEntityException(String message, Throwable cause, List<String> validationErrors) {
        super(message, cause);
        this.validationErrors = validationErrors;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

}
