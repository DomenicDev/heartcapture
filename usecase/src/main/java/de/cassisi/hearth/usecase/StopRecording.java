package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.RecordSession;
import de.cassisi.hearth.usecase.port.RecordingManager;

import java.util.Optional;

public class StopRecording {

    private RecordingManager recordingManager;

    public StopRecording(RecordingManager recordingManager) {
        this.recordingManager = recordingManager;
    }

    public void stopRecording() {
        Optional<RecordSession> optRec = recordingManager.getActiveRecordingSession();
        optRec.ifPresent(recordSession -> recordingManager.stopRecording(recordSession));
    }

}
