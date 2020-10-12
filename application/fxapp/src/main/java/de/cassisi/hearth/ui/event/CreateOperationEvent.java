package de.cassisi.hearth.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public final class CreateOperationEvent {

    private final LocalDate operationDate;
    private final String roomNr;

}
