package de.cassisi.heartcapture.ui.event;

import javafx.stage.Window;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpenRecordingDialogEvent {

    private final Window owner;
    private final long operationId;

}
