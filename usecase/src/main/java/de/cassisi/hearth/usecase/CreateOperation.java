package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.time.LocalDate;

import static de.cassisi.hearth.usecase.CreateOperation.*;

public interface CreateOperation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public LocalDate localDate;
        public String room;
    }

    class OutputData {
        public SimpleOperationData operationData;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws
            InputValidationException;
}
