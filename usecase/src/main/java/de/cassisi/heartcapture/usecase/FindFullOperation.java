package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import static de.cassisi.heartcapture.usecase.FindFullOperation.*;

public interface FindFullOperation extends UseCaseTemplate<InputData, CompleteOperationDataDTO> {

    class InputData {
        public long operationId;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<CompleteOperationDataDTO> outputHandler) throws
            OperationNotFoundException;
}
