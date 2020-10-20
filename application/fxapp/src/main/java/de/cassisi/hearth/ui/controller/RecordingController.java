package de.cassisi.hearth.ui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.recorder.BISSimulation;
import de.cassisi.hearth.ui.recorder.InfusionSimulation;
import de.cassisi.hearth.ui.recorder.NIRSSimulation;
import de.cassisi.hearth.ui.recorder.Recorder;
import de.cassisi.hearth.ui.recorder.data.BISData;
import de.cassisi.hearth.ui.recorder.data.InfusionData;
import de.cassisi.hearth.ui.recorder.data.NIRSData;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import org.springframework.stereotype.Component;

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
        Recorder<NIRSData> nirs = new NIRSSimulation();
        Recorder<BISData> bis = new BISSimulation();
        Recorder<InfusionData> infusion = new InfusionSimulation();

        nirs.setListener(data -> eventBus.post(new AddNirsDataEvent(data.timestamp, data.left, data.right, operationId)));
        bis.setListener(data -> eventBus.post(new AddAnesthesiaDataEvent(data.timestamp, data.depthOfAnesthesia, operationId)));
        infusion.setListener(data -> eventBus.post(new AddInfusionDataEvent(data.timestamp, convert(data.perfusions), operationId)));

        // add recorders
        session.add(nirs);
        session.add(bis);
        session.add(infusion);

        // start
        session.start();

        this.currentSession = session;
        running = true;
    }

    @Subscribe
    public void handle(StopRecordingEvent event) {
        if (!isRunning()) {
            // no session running
            return;
        }

        this.running = false;
        this.currentSession.stop();
        this.currentSession = null;
    }

    private List<AddInfusionDataEvent.PerfusionData> convert(List<InfusionData.Perfusion> perfusions) {
        List<AddInfusionDataEvent.PerfusionData> result = new ArrayList<>();
        perfusions.forEach(data -> result.add(new AddInfusionDataEvent.PerfusionData(data.name, data.rate)));
        return result;
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
