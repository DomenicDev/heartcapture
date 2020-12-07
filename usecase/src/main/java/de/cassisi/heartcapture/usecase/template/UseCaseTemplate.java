package de.cassisi.heartcapture.usecase.template;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import lombok.NonNull;

public interface UseCaseTemplate<InputData, OutputData> {

    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler);

}
