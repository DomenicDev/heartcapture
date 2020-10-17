package de.cassisi.hearth.ui.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;

@Getter
@AllArgsConstructor
public class AddHlmFileToOperationEvent {

    private final long operationId;
    @NonNull private final File hlmFile;

}
