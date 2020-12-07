package de.cassisi.heartcapture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Represents one sample of NIRS data.
 */
@Getter
@AllArgsConstructor
public final class NIRSData {

    @NonNull private final LocalDateTime timestamp;
    private final int leftSaturation;
    private final int rightSaturation;

}
