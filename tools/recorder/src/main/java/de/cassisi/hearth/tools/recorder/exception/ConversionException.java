package de.cassisi.hearth.tools.recorder.exception;

public class ConversionException extends RuntimeException {

    public ConversionException(String data, Class<?> resultType) {
        super("could not convert data '" + data + "' to result type: " + resultType.getSimpleName());
    }
}
