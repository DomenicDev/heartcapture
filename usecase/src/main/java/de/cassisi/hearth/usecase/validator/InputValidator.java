package de.cassisi.hearth.usecase.validator;

import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.exception.*;

import java.time.LocalDateTime;
import java.util.List;

public final class InputValidator {

    public static void heckNotNull(Object object) {
        if (object == null) {
            throw new InputValidationException(new NullPointerException("supplied object must not be null"));
        }
    }

    public static void checkNotNull(Object object, String name) {
        if (object == null) {
            throw new InputValidationException(new NullPointerException("Property '" + name + "' must not be null"));
        }
    }

    public static void checkNotNegative(long value) {
        if (value < 0) {
            throw new InputValidationException(new IllegalArgumentException("value must not be below zero"));
        }
    }

    public static void checkNotNegative(double value) {
        if (value < 0) {
            throw new InputValidationException(new IllegalArgumentException("value must not be below zero"));
        }
    }

    public static void checkIdPositive(long id) {
        if (id < 0) {
            throw new IdMustBePositiveException(id);
        }
    }

    public static void checkDepthOfAnesthesia(double depth) {
        if (depth < 0 || depth > 100) {
            throw new InputValidationException(new InvalidDepthOfAnesthesiaException(depth));
        }
    }

    public static void checkTimestamp(LocalDateTime timestamp) throws InvalidTimestampException {
        if (timestamp == null) {
            throw new InputValidationException(new InvalidTimestampException(null));
        }
    }

    public static void checkPerfusorInput(List<AddInfusionData.InputData.PerfusorInput> inputs) {
        if (inputs == null) {
            throw new InputValidationException(new NullPointerException("perfusor list must not be null (but may be empty)"));
        }
        inputs.forEach((data) -> {
            if (data == null || data.rate < 0 || data.name.isEmpty()) {
                throw new InputValidationException(new InvalidPerfusorInputException());
            }
        });
    }

    public static void checkNirsSaturation(double saturation) {
        if (saturation < 0) {
            throw new InputValidationException(new InvalidNirsSaturationException(saturation));
        }
    }

    public static void checkNotNullOrBlank(String s) {
        if (s == null || s.isBlank()) {
            throw new InputValidationException("supplied String must not be null or blank");
        }
    }
}
