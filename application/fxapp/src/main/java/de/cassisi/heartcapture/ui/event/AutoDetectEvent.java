package de.cassisi.heartcapture.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AutoDetectEvent {

    private final boolean bisEnabled;
    private final boolean nirsEnables;
    private final boolean infusionEnabled;

}
