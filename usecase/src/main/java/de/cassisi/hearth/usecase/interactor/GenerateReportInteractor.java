package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.usecase.GenerateReport;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.GenerateReportRepository;
import de.cassisi.hearth.usecase.port.ReportFileGenerator;
import de.cassisi.hearth.usecase.validator.InputValidator;

import java.io.File;
import java.util.List;

public class GenerateReportInteractor implements GenerateReport {

    private GenerateReportRepository repository;
    private ReportFileGenerator fileGenerator;

    public GenerateReportInteractor(GenerateReportRepository repository, ReportFileGenerator fileGenerator) {
        this.repository = repository;
        this.fileGenerator = fileGenerator;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        long operationId = input.operationId;
        InputValidator.checkIdPositive(operationId);

        // gather data
        Operation operation = repository.findOperation(operationId);
        HLMData hlmData = repository.findHLMData(operationId);
        List<AnesthesiaData> anesthesiaData = repository.findAnesthesiaData(operationId);
        List<InfusionData> infusionData = repository.findInfusionData(operationId);
        List<NIRSData> nirsData = repository.findNirsData(operationId);

        // generate file
        byte[] reportFile = fileGenerator.generateReport(operation, hlmData, infusionData, anesthesiaData, nirsData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.reportFile = reportFile;
        outputHandler.handle(outputData);
    }
}
