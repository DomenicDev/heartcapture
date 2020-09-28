package de.cassisi.hearth.usecase.validator;

import de.cassisi.hearth.usecase.exception.IdMustBePositiveException;
import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.exception.InvalidDepthOfAnesthesiaException;
import de.cassisi.hearth.usecase.exception.InvalidTimestampException;

import java.time.LocalDateTime;

public final class InputValidator {

    public static void heckNotNull(Object object) {
        if (object == null) {
            throw new InputValidationException("Object must not be null");
        }
    }

    public static void checkNotNull(Object object, String name) {
        if (object == null) {
            throw new InputValidationException("Property '" + name + "' must not be null");
        }
    }

    public static void checkNotNegative(long value) {
        if (value < 0) {
            throw new InputValidationException("value must not be below zero");
        }
    }

    public static void checkNotNegative(double value) {
        if (value < 0) {
            throw new InputValidationException("value must not be below zero");
        }
    }

    public static void checkIdPositive(long id) {
        if (id < 0) {
            throw new IdMustBePositiveException(id);
        }
    }

    public static void checkDepthOfAnesthesia(double depth) {
        if (depth < 0 || depth > 100) {
            throw new InvalidDepthOfAnesthesiaException(depth);
        }
    }

    public static void checkTimestamp(LocalDateTime timestamp) throws InvalidTimestampException {
        if (timestamp == null) {
            throw new InvalidTimestampException(timestamp);
        }
    }
}
