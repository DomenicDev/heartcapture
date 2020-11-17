package de.cassisi.hearth.ui.interactor;

import de.cassisi.hearth.ui.exception.ExceptionHandler;
import de.cassisi.hearth.ui.presenter.OperationOverviewPresenter;
import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

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

    private final ExceptionHandler exceptionHandler;

    private final Executor executor = new Executor();

    public DefaultExecutor(AddNirsData addNirsData, AddAnesthesiaData addAnesthesiaData, AddInfusionData addInfusionData, CreateOperation createOperation, FindAllOperations findAllOperations, FindOperation findOperation, FindFullOperation findFullOperation, ReadHLMDataFile readHLMDataFile, GenerateReport generateReport, ExceptionHandler exceptionHandler) {
        this.addNirsData = addNirsData;
        this.addAnesthesiaData = addAnesthesiaData;
        this.addInfusionData = addInfusionData;
        this.createOperation = createOperation;
        this.findAllOperations = findAllOperations;
        this.findOperation = findOperation;
        this.findFullOperation = findFullOperation;
        this.readHLMDataFile = readHLMDataFile;
        this.generateReport = generateReport;
        this.exceptionHandler = exceptionHandler;

        this.executor.start();
    }

    @Override
    public void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler) {
        add(() -> addNirsData.execute(inputData, outputHandler));
    }

    @Override
    public void addAnesthesiaData(AddAnesthesiaData.InputData inputData, OutputHandler<AddAnesthesiaData.OutputData> outputHandler) {
        add(() -> addAnesthesiaData.execute(inputData, outputHandler));
    }

    @Override
    public void addInfusionData(AddInfusionData.InputData inputData, OutputHandler<AddInfusionData.OutputData> outputHandler) {
        add(() -> addInfusionData.execute(inputData, outputHandler));
    }

    @Override
    public void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler) {
        add(() -> createOperation.execute(inputData, outputHandler));
    }

    @Override
    public void findAllOperations(FindAllOperations.InputData inputData, OutputHandler<FindAllOperations.OutputData> outputHandler) {
        add(() -> findAllOperations.execute(inputData,outputHandler));
    }

    @Override
    public void findOperation(FindOperation.InputData inputData, OperationOverviewPresenter operationOverviewPresenter) {
        add(() -> findOperation.execute(inputData, operationOverviewPresenter));
    }

    @Override
    public void findFullOperation(FindFullOperation.InputData inputData, OutputHandler<CompleteOperationDataDTO> outputHandler) {
        add(() -> findFullOperation.execute(inputData, outputHandler));
    }

    @Override
    public void readHlmDataFile(ReadHLMDataFile.InputData inputData, OutputHandler<ReadHLMDataFile.OutputData> outputHandler) {
        add(() -> readHLMDataFile.execute(inputData, outputHandler));
    }

    @Override
    public void generateReportEvent(GenerateReport.InputData inputData, OutputHandler<GenerateReport.OutputData> outputHandler) {
        add(() -> generateReport.execute(inputData, outputHandler));
    }

    private void add(Job job) {
        this.executor.add(job);
    }


    private class Executor implements Runnable {

        private final Queue<Job> jobs = new ConcurrentLinkedDeque<>();
        private final Thread executorThread = new Thread(this);
        private boolean active = false;

        Executor() {
            this.executorThread.setName("UseCase Executor");
            this.executorThread.setDaemon(true);
        }

        void add(Job job) {
            this.jobs.add(job);
        }

        void start() {
            this.active = true;
            this.executorThread.start();
        }

        void stop() {
            this.active = false;
        }

        @Override
        public void run() {
            while (active) {
                Job job = jobs.poll();
                if (job != null) {
                    try {
                        job.execute();
                    } catch (Exception e) {
                        exceptionHandler.handleGenericException(e);
                    }
                }
            }
        }
    }

    private interface Job {

        void execute();

    }
}
