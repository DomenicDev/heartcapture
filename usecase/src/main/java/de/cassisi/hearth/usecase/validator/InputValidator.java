package de.cassisi.hearth.usecase.validator;

import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.exception.*;

import java.time.LocalDateTime;
import java.util.List;

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

    public static void checkPerfusorInput(List<AddInfusionData.InputData.PerfusorInput> inputs) {
        if (inputs == null) {
            throw new InvalidPerfusorInputException();
        }
        inputs.forEach((data) -> {
            if (data == null || data.rate < 0 || data.name.isEmpty()) {
                throw new InvalidPerfusorInputException();
            }
        });
    }
}
