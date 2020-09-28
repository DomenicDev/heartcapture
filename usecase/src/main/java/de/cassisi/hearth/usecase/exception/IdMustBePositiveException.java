package de.cassisi.hearth.usecase.exception;

public class IdMustBePositiveException extends RuntimeException {

    public IdMustBePositiveException(long id) {
        super("The specified id must be positive. Value: " + id);
    }
}
