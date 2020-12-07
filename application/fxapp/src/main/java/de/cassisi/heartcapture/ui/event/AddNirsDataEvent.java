package de.cassisi.heartcapture.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class AddNirsDataEvent {

    private final LocalDateTime timestamp;
    private final int left;
    private final int right;
    private final long operationId;

}
