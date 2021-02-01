package de.cassisi.heartcapture.ui.interactor;

import de.cassisi.heartcapture.usecase.*;
import de.cassisi.heartcapture.usecase.dto.PreMedicationDataDTO;
import de.cassisi.heartcapture.usecase.exception.*;
import de.cassisi.heartcapture.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.heartcapture.usecase.dto.SimpleStatisticDTO;
import de.cassisi.heartcapture.usecase.output.OutputHandler;

public interface UseCaseExecutor {

    void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException;

    void addAnesthesiaData(AddAnesthesiaData.InputData inputData, OutputHandler<AddAnesthesiaData.OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException;

    void addInfusionData(AddInfusionData.InputData inputData, OutputHandler<AddInfusionData.OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException;

    void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler) throws InputValidationException;

    void findAllOperations(FindAllOperations.InputData inputData, OutputHandler<FindAllOperations.OutputData> outputHandler);

    void findOperation(FindOperation.InputData inputData, OutputHandler<FindOperation.OutputData> operationOverviewPresenter) throws OperationNotFoundException;

    void findFullOperation(FindFullOperation.InputData inputData, OutputHandler<CompleteOperationDataDTO> outputHandler) throws InputValidationException, OperationNotFoundException;

    void readHlmDataFile(ReadHLMDataFile.InputData inputData, OutputHandler<ReadHLMDataFile.OutputData> outputHandler) throws OperationNotFoundException, ReadHLMFileException, OperationLockException;

    void generateReport(GenerateReport.InputData inputData, OutputHandler<GenerateReport.OutputData> outputHandler) throws OperationNotFoundException, MissingHlmFileException, ReportGenerationException;

    void generateStatistic(OutputHandler<SimpleStatisticDTO> outputHandler);

    void setLockState(LockOperation.InputData inputData, OutputHandler<LockOperation.OutputData> outputHandler) throws OperationNotFoundException;

    void editOperationInformation(EditOperationInformation.InputData inputData, OutputHandler<EditOperationInformation.OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException;

    void findPreMedicationData(FindMedicationData.InputData inputData, OutputHandler<PreMedicationDataDTO> outputHandler);

    void updatePreMedicationData(UpdatePreMedicationData.InputData inputData, OutputHandler<UpdatePreMedicationData.OutputData> outputHandler);
}
