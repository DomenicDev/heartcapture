package de.cassisi.heartcapture.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class StopRecordingEvent {

    private final long operationId;
}
