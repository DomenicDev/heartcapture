package de.cassisi.heartcapture.tools.recorder.bis;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public final class BISData {

    public final LocalDateTime timestamp;
    public final double depthOfAnesthesia;

}
