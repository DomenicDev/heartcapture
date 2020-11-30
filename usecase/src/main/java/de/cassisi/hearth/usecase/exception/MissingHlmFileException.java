package de.cassisi.hearth.usecase.exception;

public class MissingHlmFileException extends RuntimeException {

    public MissingHlmFileException(long operationId) {
        super("Missing HLM data for operation #" + operationId);
    }
}
