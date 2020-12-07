package de.cassisi.heartcapture.usecase.exception;

public class InvalidNirsSaturationException extends RuntimeException {

    public InvalidNirsSaturationException(double saturation) {
        super("Invalid NIRS saturation specified: " + saturation);
    }
}
