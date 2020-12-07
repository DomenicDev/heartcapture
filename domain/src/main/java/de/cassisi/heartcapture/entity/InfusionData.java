package de.cassisi.heartcapture.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * InfusionData represents one data sample containing current perfusor names and flow rates.
 */
@Getter
public final class InfusionData {

    private final LocalDateTime timestamp;
    private final List<PerfusorData> perfusorDataList;

    /**
     * Creates an Infusion Data object with the specified timestamp and perfusor data.
     * Note that the perfusor data list will be added to an immutable list.
     *
     * @param timestamp    the infusion data timestamp
     * @param perfusorDataList the perfusor data
     */
    public InfusionData(LocalDateTime timestamp, List<PerfusorData> perfusorDataList) {
        this.timestamp = timestamp;
        this.perfusorDataList = Collections.unmodifiableList(perfusorDataList);
    }
}
