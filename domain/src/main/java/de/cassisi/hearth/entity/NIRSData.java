package de.cassisi.hearth.entity;

import java.time.LocalDateTime;

public final class NIRSData {

    private final int leftSaturation;
    private final int rightSaturation;
    private final LocalDateTime timestamp;

    public NIRSData(int leftSaturation, int rightSaturation, LocalDateTime timestamp) {
        this.leftSaturation = leftSaturation;
        this.rightSaturation = rightSaturation;
        this.timestamp = timestamp;
    }

    public int getLeftSaturation() {
        return leftSaturation;
    }

    public int getRightSaturation() {
        return rightSaturation;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
