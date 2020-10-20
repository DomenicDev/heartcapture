package de.cassisi.hearth.ui.recorder.data;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public final class BISData {

    public final LocalDateTime timestamp;
    public final double depthOfAnesthesia;

}
