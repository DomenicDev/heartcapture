package de.cassisi.hearth.usecase.exception;

public class OperationLockException extends RuntimeException {

    public OperationLockException(long operationId) {
        super("Operation #" + operationId + " is locked.");
    }
}
