package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.dto.SimpleOperationData;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDate;

import static de.cassisi.heartcapture.usecase.EditOperationInformation.*;

public interface EditOperationInformation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
        public LocalDate operationDate;
        public String operationRoom;
    }

    class OutputData {
        public SimpleOperationData operationData;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException;

}
