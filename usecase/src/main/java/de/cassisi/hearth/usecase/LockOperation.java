package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import static de.cassisi.hearth.usecase.LockOperation.*;

public interface LockOperation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
    }

    class OutputData {
        public SimpleOperationData operationData;
    }
}
