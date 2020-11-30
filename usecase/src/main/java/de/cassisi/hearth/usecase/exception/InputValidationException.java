package de.cassisi.hearth.usecase.exception;

public class InputValidationException extends RuntimeException {

    public InputValidationException(String message) {
        super(message);
    }

    public InputValidationException(Throwable cause) {
        super(cause);
    }
}
