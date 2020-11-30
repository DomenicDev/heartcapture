package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.exception.OperationLockException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDateTime;

import static de.cassisi.hearth.usecase.AddAnesthesiaData.*;

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
