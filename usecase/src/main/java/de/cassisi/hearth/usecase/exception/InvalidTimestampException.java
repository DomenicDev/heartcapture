package de.cassisi.hearth.usecase.exception;

import java.time.LocalDateTime;

public class InvalidTimestampException extends RuntimeException {

    public InvalidTimestampException(LocalDateTime localDateTime) {
        super("Specified value is not a valid timestamp: " + localDateTime);
    }

}
