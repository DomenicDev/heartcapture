package de.cassisi.hearth.ui.interactor;

import de.cassisi.hearth.ui.presenter.OperationOverviewPresenter;
import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.output.OutputHandler;

public interface UseCaseExecutor {

    void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler);

    void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler);

    void findAllOperations(FindAllOperations.InputData inputData, OutputHandler<FindAllOperations.OutputData> outputHandler);

    void findOperation(FindOperation.InputData inputData, OperationOverviewPresenter operationOverviewPresenter);

    void readHlmDataFile(ReadHLMDataFile.InputData inputData, OutputHandler<ReadHLMDataFile.OutputData> outputHandler);

    void generateReportEvent(GenerateReport.InputData inputData, OutputHandler<GenerateReport.OutputData> outputHandler);
}
