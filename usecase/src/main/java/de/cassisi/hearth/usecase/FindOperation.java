package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import static de.cassisi.hearth.usecase.FindOperation.*;

public interface FindOperation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        long operationId;
    }

    class OutputData {

    }

}
