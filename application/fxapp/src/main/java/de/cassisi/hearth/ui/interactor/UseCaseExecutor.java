package de.cassisi.hearth.ui.interactor;

import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;

public interface UseCaseExecutor {

    void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler);

    void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler);

}
