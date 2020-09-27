package de.cassisi.hearth.usecase.exception;

public class OperationNotFoundException extends RuntimeException {

    public OperationNotFoundException(long id) {
        super("Operation entity with id " + id + " could not be found.");
    }
}
