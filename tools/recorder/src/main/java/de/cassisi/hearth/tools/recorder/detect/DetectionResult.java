package de.cassisi.hearth.tools.recorder.detect;

import java.util.HashMap;

public class DetectionResult {

    private final HashMap<String, String> devicePortMap = new HashMap<>();

    public String get(String device) {
        return devicePortMap.get(device);
    }

    void put(String device, String address) {
        this.devicePortMap.put(device, address);
    }

}
