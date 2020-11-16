package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import static de.cassisi.hearth.usecase.FindFullOperation.*;

public interface FindFullOperation extends UseCaseTemplate<InputData, CompleteOperationDataDTO> {

    class InputData {
        public long operationId;
    }

}
