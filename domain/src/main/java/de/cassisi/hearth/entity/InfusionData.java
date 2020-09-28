package de.cassisi.hearth.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
public final class InfusionData {

    private final LocalDateTime localDateTime;
    private final List<PerfusorData> perfusorDataList;

    /**
     * Creates an Infusion Data object with the specified timestamp and perfusor data.
     * Note that the perfusor data list will be added to an immutable list.
     *
     * @param localDateTime the infusion data timestamp
     * @param perfusorDataList the perfusor data
     */
    public InfusionData(LocalDateTime localDateTime, List<PerfusorData> perfusorDataList) {
        this.localDateTime = localDateTime;
        this.perfusorDataList = Collections.unmodifiableList(perfusorDataList);
    }
}
