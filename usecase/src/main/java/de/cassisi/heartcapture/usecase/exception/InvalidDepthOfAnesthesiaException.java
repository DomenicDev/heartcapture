package de.cassisi.heartcapture.usecase.exception;

public class InvalidDepthOfAnesthesiaException extends RuntimeException {

    public InvalidDepthOfAnesthesiaException(double value) {
        super("Invalid value for depth of anesthesia: " + value);
    }

}
