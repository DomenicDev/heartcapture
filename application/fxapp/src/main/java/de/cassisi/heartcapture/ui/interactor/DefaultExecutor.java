package de.cassisi.heartcapture.ui.interactor;

import de.cassisi.heartcapture.usecase.*;
import de.cassisi.heartcapture.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.heartcapture.usecase.dto.SimpleStatisticDTO;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultExecutor implements UseCaseExecutor {

    private final AddNirsData addNirsData;
    private final AddAnesthesiaData addAnesthesiaData;
    private final AddInfusionData addInfusionData;
    private final CreateOperation createOperation;
    private final FindAllOperations findAllOperations;
    private final FindOperation findOperation;
    private final FindFullOperation findFullOperation;
    private final ReadHLMDataFile readHLMDataFile;
    private final GenerateReport generateReport;
    private final GenerateStatistic generateStatistic;
    private final LockOperation lockOperation;
    private final EditOperationInformation editOperationInformation;

    public DefaultExecutor(AddNirsData addNirsData, AddAnesthesiaData addAnesthesiaData, AddInfusionData addInfusionData, CreateOperation createOperation, FindAllOperations findAllOperations, FindOperation findOperation, FindFullOperation findFullOperation, ReadHLMDataFile readHLMDataFile, GenerateReport generateReport, GenerateStatistic generateStatistic, LockOperation lockOperation, EditOperationInformation editOperationInformation) {
        this.addNirsData = addNirsData;
        this.addAnesthesiaData = addAnesthesiaData;
        this.addInfusionData = addInfusionData;
        this.createOperation = createOperation;
        this.findAllOperations = findAllOperations;
        this.findOperation = findOperation;
        this.findFullOperation = findFullOperation;
        this.readHLMDataFile = readHLMDataFile;
        this.generateReport = generateReport;
        this.generateStatistic = generateStatistic;
        this.lockOperation = lockOperation;
        this.editOperationInformation = editOperationInformation;
    }

    @Override
    public void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler) {
        addNirsData.execute(inputData, outputHandler);
    }

    @Override
    public void addAnesthesiaData(AddAnesthesiaData.InputData inputData, OutputHandler<AddAnesthesiaData.OutputData> outputHandler) {
       addAnesthesiaData.execute(inputData, outputHandler);
    }

    @Override
    public void addInfusionData(AddInfusionData.InputData inputData, OutputHandler<AddInfusionData.OutputData> outputHandler) {
       addInfusionData.execute(inputData, outputHandler);
    }

    @Override
    public void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler) {
        createOperation.execute(inputData, outputHandler);
    }

    @Override
    public void findAllOperations(FindAllOperations.InputData inputData, OutputHandler<FindAllOperations.OutputData> outputHandler) {
        findAllOperations.execute(inputData,outputHandler);
    }

    @Override
    public void findOperation(FindOperation.InputData inputData, OutputHandler<FindOperation.OutputData> operationOverviewPresenter) {
        findOperation.execute(inputData, operationOverviewPresenter);
    }

    @Override
    public void findFullOperation(FindFullOperation.InputData inputData, OutputHandler<CompleteOperationDataDTO> outputHandler) {
        findFullOperation.execute(inputData, outputHandler);
    }

    @Override
    public void readHlmDataFile(ReadHLMDataFile.InputData inputData, OutputHandler<ReadHLMDataFile.OutputData> outputHandler) {
        readHLMDataFile.execute(inputData, outputHandler);
    }

    @Override
    public void generateReport(GenerateReport.InputData inputData, OutputHandler<GenerateReport.OutputData> outputHandler) {
        generateReport.execute(inputData, outputHandler);
    }

    @Override
    public void generateStatistic(OutputHandler<SimpleStatisticDTO> outputHandler) {
        generateStatistic.execute(null, outputHandler);
    }

    @Override
    public void setLockState(LockOperation.InputData inputData, OutputHandler<LockOperation.OutputData> outputHandler) throws OperationNotFoundException {
        lockOperation.execute(inputData, outputHandler);
    }

    @Override
    public void editOperationInformation(EditOperationInformation.InputData inputData, OutputHandler<EditOperationInformation.OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException {
        editOperationInformation.execute(inputData, outputHandler);
    }
}
