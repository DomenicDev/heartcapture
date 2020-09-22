package de.cassisi.hearth.entity;

import java.time.LocalDateTime;

public final class AnesthesiaData {

    private final int depthOfAnesthesia;
    private final LocalDateTime timestamp;

    public AnesthesiaData(int depthOfAnesthesia, LocalDateTime timestamp) {
        this.depthOfAnesthesia = depthOfAnesthesia;
        this.timestamp = timestamp;
    }

    public int getDepthOfAnesthesia() {
        return depthOfAnesthesia;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
