package de.cassisi.hearth.usecase.exception;

public class InvalidDepthOfAnesthesiaException extends RuntimeException {

    public InvalidDepthOfAnesthesiaException(double value) {
        super("Invalid value for depth of anesthesia: " + value);
    }

}
