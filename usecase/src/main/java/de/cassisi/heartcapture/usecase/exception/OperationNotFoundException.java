package de.cassisi.heartcapture.usecase.exception;

public class OperationNotFoundException extends RuntimeException {

    private final long id;

    public OperationNotFoundException(long id) {
        super("Operation entity with id " + id + " could not be found.");
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
