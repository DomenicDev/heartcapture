package de.cassisi.hearth.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EditOperationInformationEvent {

    private final long operationId;
    private final LocalDate operationDate;
    private final String operationRoom;

}
