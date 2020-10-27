package de.cassisi.hearth.ui.recorder;

import com.google.common.eventbus.EventBus;
import de.cassisi.hearth.tools.recorder.Profile;
import de.cassisi.hearth.tools.recorder.RecordSession;
import de.cassisi.hearth.tools.recorder.exception.RecorderException;
import de.cassisi.hearth.tools.recorder.infusion.InfusionData;
import de.cassisi.hearth.ui.enums.MessageType;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecordingController {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    private boolean running = false;
    private RecordSession currentSession = null;

    public RecordingController() {
        this.eventBus.register(this);
    }

    public boolean startRecording(StartRecordingEvent event) {
        if (isRunning()) {
            postMessage("ui.message.info_recording_session_already_running", MessageType.INFO);
            return false;
        }

        try {
            long operationId = event.getId();

            Profile profile = createProfileFromEvent(event, operationId);
            RecordSession session = RecordSession.create(profile);
            session.startRecording();

            // update global fields
            this.currentSession = session;
            this.running = true;
            postMessage("ui.message.info_recording_started", MessageType.INFO);
            return true;
        } catch (RecorderException e) {
            postMessage("ui.message.error_start_recording", MessageType.ERROR);
            return false;
        }
    }

    public boolean stopRecording() {
        if (!isRunning()) {
            postMessage("ui.message.info_recording_session_not_active", MessageType.INFO);
            return false;
        }

        try {
            this.currentSession.stopRecording();
            this.running = false;
            this.currentSession = null;
            postMessage("ui.message.info_recording_stopped", MessageType.INFO);
            return true;
        } catch (RecorderException e) {
            postMessage("ui.message.error_stop_recording", MessageType.ERROR);
            return false;
        }
    }

    private Profile createProfileFromEvent(StartRecordingEvent event, long operationId) {
        Profile profile = new Profile();

        // BSI
        if (event.isUseBISSerial()) {
            profile.addBisRS232Recorder(event.getBisSerialPort(),
                    data -> eventBus.post(new AddAnesthesiaDataEvent(
                            data.timestamp,
                            data.depthOfAnesthesia,
                            operationId)));
        }

        // Infusion
        if (event.isUseInfusionSerial()) {
            profile.addInfusionRS232Recorder(event.getInfusionSerialPort(),
                    data -> eventBus.post(new AddInfusionDataEvent(
                            data.timestamp,
                            convert(data.perfusions),
                            operationId)));
        }

        // NIRS
        if (event.isUseNIRSSerial()) {
            profile.addNirsRS232Recorder(event.getNirsSerialPort(),
                    data -> eventBus.post(new AddNirsDataEvent(
                            data.timestamp,
                            data.left,
                            data.right, operationId)));
        }
        return profile;
    }

    private List<AddInfusionDataEvent.PerfusionData> convert(List<InfusionData.Perfusion> perfusions) {
        List<AddInfusionDataEvent.PerfusionData> result = new ArrayList<>();
        perfusions.forEach(data -> result.add(new AddInfusionDataEvent.PerfusionData(data.name, data.rate)));
        return result;
    }


    public boolean isRunning() {
        return running;
    }

    private void postMessage(String key, MessageType messageType) {
        this.eventBus.post(new ShowMessageEvent(key, messageType));
    }

}
