package de.cassisi.hearth.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LockOperationEvent {

    private final long operationId;
    private final boolean locked;

}
