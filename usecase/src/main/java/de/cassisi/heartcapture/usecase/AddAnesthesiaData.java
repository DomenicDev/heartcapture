package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDateTime;

import static de.cassisi.heartcapture.usecase.AddAnesthesiaData.*;

public interface AddAnesthesiaData extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
        public LocalDateTime timestamp;
        public double depthOfAnesthesia;
    }

    class OutputData {
        public long operationId;
        public LocalDateTime timestamp;
        public double depthOfAnesthesia;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws
            InputValidationException,
            OperationNotFoundException,
            OperationLockException;
}
