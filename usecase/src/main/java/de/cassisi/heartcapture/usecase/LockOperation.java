package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.dto.SimpleOperationData;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import static de.cassisi.heartcapture.usecase.LockOperation.InputData;
import static de.cassisi.heartcapture.usecase.LockOperation.OutputData;

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
