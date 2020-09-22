package de.cassisi.hearth.port;

import de.cassisi.hearth.dto.RecordParams;
import de.cassisi.hearth.dto.RecordSession;

import java.util.Optional;

public interface RecordingManager {

    RecordSession setup(RecordParams recordParams);

    void startRecording(RecordSession recordSession);

    void stopRecording(RecordSession recordSession);

    Optional<RecordSession> getActiveRecordingSession();

}
