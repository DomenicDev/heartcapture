package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.usecase.dto.RecordParams;
import de.cassisi.hearth.usecase.dto.RecordSession;

import java.util.Optional;

public interface RecordingManager {

    RecordSession setup(RecordParams recordParams);

    void startRecording(RecordSession recordSession);

    void stopRecording(RecordSession recordSession);

    Optional<RecordSession> getActiveRecordingSession();

}
