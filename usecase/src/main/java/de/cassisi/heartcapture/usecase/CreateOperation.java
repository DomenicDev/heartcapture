package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDate;

import static de.cassisi.heartcapture.usecase.CreateOperation.InputData;
import static de.cassisi.heartcapture.usecase.CreateOperation.OutputData;

public interface CreateOperation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public LocalDate localDate;
        public String room;
    }

    class OutputData {
        public CompleteOperationDataDTO operationData;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws
            InputValidationException;
}
