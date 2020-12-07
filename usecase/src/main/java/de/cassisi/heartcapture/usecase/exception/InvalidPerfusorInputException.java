package de.cassisi.heartcapture.usecase.exception;

public class InvalidPerfusorInputException extends RuntimeException {

    public InvalidPerfusorInputException() {
        super("Perfusor input data is either null or not valid");
    }
}
