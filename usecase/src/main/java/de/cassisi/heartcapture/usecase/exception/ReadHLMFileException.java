package de.cassisi.heartcapture.usecase.exception;

public class ReadHLMFileException extends RuntimeException {

    public ReadHLMFileException(String message) {
        super(message);
    }

    public ReadHLMFileException(Throwable cause) {
        super(cause);
    }
}
