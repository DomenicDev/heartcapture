package de.cassisi.hearth.ui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.ui.event.AddNirsDataEvent;
import de.cassisi.hearth.ui.event.StartRecordingEvent;
import de.cassisi.hearth.ui.recorder.NIRSSimulation;
import de.cassisi.hearth.ui.recorder.Recorder;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RecordingController {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    private boolean running = false;
    private RecorderSession currentSession = null;

    public RecordingController() {
        this.eventBus.register(this);
    }

    @Subscribe
    public void handle(StartRecordingEvent event) {
        if (isRunning()) {
            // todo some notification
            return;
        }
        long operationId = event.getId();

        RecorderSession session = new RecorderSession();
        // setup recorders
        NIRSSimulation nirs = new NIRSSimulation();
        nirs.setListener(data -> eventBus.post(new AddNirsDataEvent(LocalDateTime.now(), data.left, data.right, operationId)));

        // add recorders
        session.add(nirs);

        // start
        session.start();

        this.currentSession = session;
        running = true;
    }

    public boolean isRunning() {
        return running;
    }


    private static class RecorderSession {

        private final List<Recorder<?>> recorderList = new ArrayList<>();

        public RecorderSession(Recorder<?>... recorders) {
            this.recorderList.addAll(Arrays.asList(recorders));
        }

        public void add(Recorder<?> recorder) {
            this.recorderList.add(recorder);
        }

        public void start() {
            recorderList.forEach(Recorder::start);
        }

        public void stop() {
            recorderList.forEach(Recorder::stop);
        }
    }

}
