package de.cassisi.heartcapture.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnesthesiaData {

    @Test
    public void createAnesthesiaDataTest() {
        LocalDateTime timestamp = LocalDateTime.now();
        double value = 12.3;

        AnesthesiaData anesthesiaData = new AnesthesiaData(timestamp, value);

        assertEquals(timestamp, anesthesiaData.getTimestamp());
        assertEquals(value, anesthesiaData.getDepthOfAnesthesia(), 0.0001);
    }

}
