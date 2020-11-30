package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import static de.cassisi.hearth.usecase.FindFullOperation.*;

public interface FindFullOperation extends UseCaseTemplate<InputData, CompleteOperationDataDTO> {

    class InputData {
        public long operationId;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<CompleteOperationDataDTO> outputHandler) throws
            OperationNotFoundException;
}
