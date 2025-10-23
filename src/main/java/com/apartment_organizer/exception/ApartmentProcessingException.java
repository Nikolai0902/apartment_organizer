package com.apartment_organizer.exception;

/**
 * Исключения бизнес логики
 */
public class ApartmentProcessingException extends RuntimeException {

    public ApartmentProcessingException(String message) {
        super(message);
    }

    public ApartmentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
