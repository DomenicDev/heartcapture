package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.exception.OperationLockException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDateTime;

import static de.cassisi.hearth.usecase.AddNirsData.InputData;
import static de.cassisi.hearth.usecase.AddNirsData.OutputData;

public interface AddNirsData extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
        public int leftSaturation;
        public int rightSaturation;
        public LocalDateTime timestamp;
    }

    class OutputData {
        public boolean saved;
        public int left;
        public int right;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler)
            throws InputValidationException, OperationNotFoundException, OperationLockException;
}
