package de.cassisi.hearth.usecase.exception;

public class SessionCreateRequestValidationException extends RuntimeException {

    public SessionCreateRequestValidationException(final String message) {
        super(message);
    }

}
