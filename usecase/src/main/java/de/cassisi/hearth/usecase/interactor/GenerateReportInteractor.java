package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.usecase.GenerateReport;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.GenerateReportRepository;
import de.cassisi.hearth.usecase.port.ReportFileGenerator;

import java.io.File;
import java.util.List;

public class GenerateReportInteractor implements GenerateReport {

    private GenerateReportRepository repository;
    private ReportFileGenerator fileGenerator;

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        long operationId = input.operationId;

        // gather data
        HLMData hlmData = repository.findHLMData(operationId);
        List<AnesthesiaData> anesthesiaData = repository.findAnesthesiaData(operationId);
        List<InfusionData> infusionData = repository.findInfusionData(operationId);
        List<NIRSData> nirsData = repository.findNirsData(operationId);

        // generate file
        File reportFile = fileGenerator.generateReport(hlmData, infusionData, anesthesiaData, nirsData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.reportFile = reportFile;
        outputHandler.handle(outputData);
    }
}
