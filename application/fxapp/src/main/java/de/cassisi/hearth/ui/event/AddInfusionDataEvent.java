package de.cassisi.hearth.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public final class AddInfusionDataEvent {

    private final LocalDateTime timestamp;
    private final List<PerfusionData> perfusions;
    private final long operationId;

    public AddInfusionDataEvent(LocalDateTime timestamp, List<PerfusionData> data, long operationId) {
        this.timestamp = timestamp;
        this.operationId = operationId;
        perfusions = Collections.unmodifiableList(data);
    }

    @AllArgsConstructor
    public static class PerfusionData {
        public final String name;
        public final double rate;
    }
}
