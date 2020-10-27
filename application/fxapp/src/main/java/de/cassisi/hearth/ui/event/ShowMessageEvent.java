package de.cassisi.hearth.ui.event;

import de.cassisi.hearth.ui.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ShowMessageEvent {

    private final String messageKey;
    private final MessageType messageType;

}
