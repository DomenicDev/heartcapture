package de.cassisi.hearth.tools.recorder.infusion;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public final class InfusionData {

    public final LocalDateTime timestamp;
    public final List<Perfusion> perfusions;

    public InfusionData(LocalDateTime timestamp, List<Perfusion> perfusions) {
        this.timestamp = timestamp;
        this.perfusions = Collections.unmodifiableList(perfusions);
    }

    @AllArgsConstructor
    public static class Perfusion {
        public final String name;
        public final double rate;
    }

}
