package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.entity.ReportData;
import de.cassisi.heartcapture.usecase.GenerateReport;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.GenerateReportRepository;
import de.cassisi.heartcapture.usecase.port.ReportFileGenerator;
import de.cassisi.heartcapture.usecase.validator.InputValidator;

public class GenerateReportInteractor implements GenerateReport {

    private final GenerateReportRepository repository;
    private final ReportFileGenerator fileGenerator;

    public GenerateReportInteractor(GenerateReportRepository repository, ReportFileGenerator fileGenerator) {
        this.repository = repository;
        this.fileGenerator = fileGenerator;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        long operationId = input.operationId;
        InputValidator.checkIdPositive(operationId);

        // gather data
        ReportData reportData = repository.getReportData(operationId);

        // generate file
        byte[] reportFile = fileGenerator.generateReport(reportData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.reportFile = reportFile;
        outputHandler.handle(outputData);
    }
}
