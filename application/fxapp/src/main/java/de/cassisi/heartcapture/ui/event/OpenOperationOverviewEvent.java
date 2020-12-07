package de.cassisi.heartcapture.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenOperationOverviewEvent {

    private final long operationId;

}
