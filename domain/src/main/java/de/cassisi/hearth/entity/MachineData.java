package de.cassisi.hearth.entity;

public class MachineData {

    private final String oxygenator;
    private final String haemoFil;

    public MachineData(String oxygenator, String haemoFil) {
        this.oxygenator = oxygenator;
        this.haemoFil = haemoFil;
    }

    public String getOxygenator() {
        return oxygenator;
    }

    public String getHaemoFil() {
        return haemoFil;
    }
}
