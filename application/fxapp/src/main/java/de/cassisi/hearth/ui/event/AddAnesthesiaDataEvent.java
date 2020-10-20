package de.cassisi.hearth.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class AddAnesthesiaDataEvent {

    private final LocalDateTime timestamp;
    private final double depthOfAnesthesia;
    private final long operationId;

}
