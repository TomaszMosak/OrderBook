package com.mthree.bsm.repository;

/**
 * Exception to be thrown when an entity in the system was searched for by its ID, but none was found.
 */
public class MissingEntityException extends Exception {

    public MissingEntityException() {
    }

    public MissingEntityException(String message) {
        super(message);
    }

    public MissingEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
