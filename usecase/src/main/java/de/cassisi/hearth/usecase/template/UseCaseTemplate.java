package de.cassisi.hearth.usecase.template;

import de.cassisi.hearth.usecase.output.OutputHandler;

public interface UseCaseTemplate<InputData, OutputData> {

    void execute(InputData input, OutputHandler<OutputData> outputHandler);

}
