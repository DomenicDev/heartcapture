package de.cassisi.heartcapture.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StartRecordingEvent {

    // maybe add some attributes
    private final long id;

    private final boolean useBISSerial;
    private final boolean useNIRSSerial;
    private final boolean useInfusionSerial;

    private final String bisSerialPort;
    private final String nirsSerialPort;
    private final String infusionSerialPort;


}
