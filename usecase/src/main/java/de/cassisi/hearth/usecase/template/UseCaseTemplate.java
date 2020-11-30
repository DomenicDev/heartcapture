package de.cassisi.hearth.usecase.template;

import de.cassisi.hearth.usecase.output.OutputHandler;
import lombok.NonNull;

public interface UseCaseTemplate<InputData, OutputData> {

    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler);

}
