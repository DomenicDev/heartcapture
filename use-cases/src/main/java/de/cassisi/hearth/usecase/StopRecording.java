package de.cassisi.hearth.usecase;

import de.cassisi.hearth.dto.RecordSession;
import de.cassisi.hearth.port.RecordingManager;

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
