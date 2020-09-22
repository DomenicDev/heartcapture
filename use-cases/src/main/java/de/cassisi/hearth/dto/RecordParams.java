package de.cassisi.hearth.dto;

public final class RecordParams {

    private final boolean infusionRecorderEnabled;
    private final boolean anesthesiaRecorderEnabled;
    private final boolean nirsRecorderEnabled;

    public RecordParams(boolean infusionRecorderEnabled, boolean anesthesiaRecorderEnabled, boolean nirsRecorderEnabled) {
        this.infusionRecorderEnabled = infusionRecorderEnabled;
        this.anesthesiaRecorderEnabled = anesthesiaRecorderEnabled;
        this.nirsRecorderEnabled = nirsRecorderEnabled;
    }

    public boolean isInfusionRecorderEnabled() {
        return infusionRecorderEnabled;
    }

    public boolean isAnesthesiaRecorderEnabled() {
        return anesthesiaRecorderEnabled;
    }

    public boolean isNirsRecorderEnabled() {
        return nirsRecorderEnabled;
    }
}
