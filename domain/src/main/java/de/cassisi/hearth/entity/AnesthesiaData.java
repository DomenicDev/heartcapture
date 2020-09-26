package de.cassisi.hearth.entity;

import java.time.LocalDateTime;

public final class AnesthesiaData {

    private final LocalDateTime timestamp;
    private final double depthOfAnesthesia;

    public AnesthesiaData(LocalDateTime timestamp, double depthOfAnesthesia) {
        this.timestamp = timestamp;
        this.depthOfAnesthesia = depthOfAnesthesia;
    }

    public double getDepthOfAnesthesia() {
        return depthOfAnesthesia;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
