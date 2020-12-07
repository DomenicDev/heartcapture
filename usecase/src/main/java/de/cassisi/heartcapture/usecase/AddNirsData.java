package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDateTime;

import static de.cassisi.heartcapture.usecase.AddNirsData.InputData;
import static de.cassisi.heartcapture.usecase.AddNirsData.OutputData;

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
