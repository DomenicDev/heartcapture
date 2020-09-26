package de.cassisi.hearth.entity;

import java.time.LocalDateTime;

public final class NIRSData {

    private final double leftSaturation;
    private final double rightSaturation;
    private final LocalDateTime timestamp;

    public NIRSData(double leftSaturation, double rightSaturation, LocalDateTime timestamp) {
        this.leftSaturation = leftSaturation;
        this.rightSaturation = rightSaturation;
        this.timestamp = timestamp;
    }

    public double getLeftSaturation() {
        return leftSaturation;
    }

    public double getRightSaturation() {
        return rightSaturation;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
