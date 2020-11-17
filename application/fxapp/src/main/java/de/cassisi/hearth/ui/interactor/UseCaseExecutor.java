package de.cassisi.hearth.ui.interactor;

import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.dto.SimpleStatisticDTO;
import de.cassisi.hearth.usecase.output.OutputHandler;

public interface UseCaseExecutor {

    void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler);

    void addAnesthesiaData(AddAnesthesiaData.InputData inputData, OutputHandler<AddAnesthesiaData.OutputData> outputHandler);

    void addInfusionData(AddInfusionData.InputData inputData, OutputHandler<AddInfusionData.OutputData> outputHandler);

    void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler);

    void findAllOperations(FindAllOperations.InputData inputData, OutputHandler<FindAllOperations.OutputData> outputHandler);

    void findOperation(FindOperation.InputData inputData, OutputHandler<FindOperation.OutputData> operationOverviewPresenter);

    void findFullOperation(FindFullOperation.InputData inputData, OutputHandler<CompleteOperationDataDTO> outputHandler);

    void readHlmDataFile(ReadHLMDataFile.InputData inputData, OutputHandler<ReadHLMDataFile.OutputData> outputHandler);

    void generateReportEvent(GenerateReport.InputData inputData, OutputHandler<GenerateReport.OutputData> outputHandler);

    void generateStatistic(OutputHandler<SimpleStatisticDTO> outputHandler);
}
