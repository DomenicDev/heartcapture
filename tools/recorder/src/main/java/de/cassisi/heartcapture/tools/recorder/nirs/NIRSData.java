package de.cassisi.heartcapture.tools.recorder.nirs;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public final class NIRSData {

    public final LocalDateTime timestamp;
    public final int left;
    public final int right;

}
