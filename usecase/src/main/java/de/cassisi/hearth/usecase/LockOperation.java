package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import static de.cassisi.hearth.usecase.LockOperation.InputData;
import static de.cassisi.hearth.usecase.LockOperation.OutputData;

public interface LockOperation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
        public boolean locked;
    }

    class OutputData {
        public SimpleOperationData operationData;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws OperationNotFoundException;
}
