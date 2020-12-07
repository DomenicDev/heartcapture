package de.cassisi.heartcapture.tools.recorder;

import de.cassisi.heartcapture.tools.recorder.bis.BISData;
import de.cassisi.heartcapture.tools.recorder.bis.BisRS232Recorder;
import de.cassisi.heartcapture.tools.recorder.infusion.InfusionData;
import de.cassisi.heartcapture.tools.recorder.infusion.InfusionRS232Recorder;
import de.cassisi.heartcapture.tools.recorder.nirs.NIRSData;
import de.cassisi.heartcapture.tools.recorder.nirs.NirsRS232Recorder;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private final List<Recorder<?>> recorderList = new ArrayList<>();

    public void addNirsRS232Recorder(String port, Callback<NIRSData> listener) {
        add(new NirsRS232Recorder(port), listener);
    }

    public void addBisRS232Recorder(String port, Callback<BISData> callback) {
        add(new BisRS232Recorder(port), callback);
    }

    public void addInfusionRS232Recorder(String port, Callback<InfusionData> callback) {
        add(new InfusionRS232Recorder(port), callback);
    }

    protected List<Recorder<?>> getRecorderList() {
        return recorderList;
    }

    private <T> void add(Recorder<T> recorder, Callback<T> callback) {
        recorder.setListener(callback);
        this.recorderList.add(recorder);
    }

}
