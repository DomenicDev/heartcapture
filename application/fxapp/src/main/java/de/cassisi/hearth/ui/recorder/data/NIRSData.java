package de.cassisi.hearth.ui.recorder.data;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class NIRSData {

    public final LocalDateTime timestamp;
    public final int left;
    public final int right;

}
