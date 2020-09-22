package de.cassisi.hearth.dto;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.listener.RecordListener;

public class RecordSession {

    private RecordParams recordParams;

    private RecordListener<InfusionData> infusionDataListener;
    private RecordListener<AnesthesiaData> anesthesiaDataListener;
    private RecordListener<NIRSData> nirsDataListener;

    public void setInfusionDataListener(RecordListener<InfusionData> infusionDataListener) {
        this.infusionDataListener = infusionDataListener;
    }

    public void setAnesthesiaDataListener(RecordListener<AnesthesiaData> anesthesiaDataListener) {
        this.anesthesiaDataListener = anesthesiaDataListener;
    }

    public void setNirsDataListener(RecordListener<NIRSData> nirsDataListener) {
        this.nirsDataListener = nirsDataListener;
    }
}
