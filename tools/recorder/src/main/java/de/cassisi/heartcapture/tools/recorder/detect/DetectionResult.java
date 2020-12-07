package de.cassisi.heartcapture.tools.recorder.detect;

import java.util.HashMap;

/**
 * Holds the auto detection information.
 * Use the public keys from {@link AutoPortDetector} for access.
 */
public class DetectionResult {

    private final HashMap<String, String> devicePortMap = new HashMap<>();

    /**
     * Return the specific detection information (e.g. port) for that device
     * @param device the device to get the information from
     * @return the port for that device
     */
    public String get(String device) {
        return devicePortMap.get(device);
    }

    public boolean containsKey(String device) {
        return devicePortMap.containsKey(device);
    }

    void put(String device, String address) {
        this.devicePortMap.put(device, address);
    }

}
