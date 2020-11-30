package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.exception.OperationLockException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDate;

import static de.cassisi.hearth.usecase.EditOperationInformation.*;

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
