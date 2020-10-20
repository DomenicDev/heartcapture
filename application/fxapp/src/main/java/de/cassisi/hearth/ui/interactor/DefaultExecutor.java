package de.cassisi.hearth.ui.interactor;

import de.cassisi.hearth.ui.presenter.OperationOverviewPresenter;
import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultExecutor implements UseCaseExecutor {

    private final AddNirsData addNirsData;
    private final AddAnesthesiaData addAnesthesiaData;
    private final AddInfusionData addInfusionData;
    private final CreateOperation createOperation;
    private final FindAllOperations findAllOperations;
    private final FindOperation findOperation;
    private final ReadHLMDataFile readHLMDataFile;
    private final GenerateReport generateReport;

    public DefaultExecutor(AddNirsData addNirsData, AddAnesthesiaData addAnesthesiaData, AddInfusionData addInfusionData, CreateOperation createOperation, FindAllOperations findAllOperations, FindOperation findOperation, ReadHLMDataFile readHLMDataFile, GenerateReport generateReport) {
        this.addNirsData = addNirsData;
        this.addAnesthesiaData = addAnesthesiaData;
        this.addInfusionData = addInfusionData;
        this.createOperation = createOperation;
        this.findAllOperations = findAllOperations;
        this.findOperation = findOperation;
        this.readHLMDataFile = readHLMDataFile;
        this.generateReport = generateReport;
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
    public void findOperation(FindOperation.InputData inputData, OperationOverviewPresenter operationOverviewPresenter) {
        findOperation.execute(inputData, operationOverviewPresenter);
    }

    @Override
    public void readHlmDataFile(ReadHLMDataFile.InputData inputData, OutputHandler<ReadHLMDataFile.OutputData> outputHandler) {
        readHLMDataFile.execute(inputData, outputHandler);
    }

    @Override
    public void generateReportEvent(GenerateReport.InputData inputData, OutputHandler<GenerateReport.OutputData> outputHandler) {
        generateReport.execute(inputData, outputHandler);
    }

}
