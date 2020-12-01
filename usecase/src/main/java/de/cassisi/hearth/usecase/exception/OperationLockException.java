package de.cassisi.hearth.usecase.exception;

public class OperationLockException extends RuntimeException {

    private final long id;

    public OperationLockException(long operationId) {
        super("Operation #" + operationId + " is locked.");
        this.id = operationId;
    }

    public long getId() {
        return id;
    }
}
