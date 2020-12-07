package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.ReportData;
import de.cassisi.heartcapture.usecase.exception.ReportGenerationException;
import de.cassisi.heartcapture.usecase.port.ReportFileGenerator;

public class ExcelReportFileGeneratorImpl implements ReportFileGenerator {

    @Override
    public byte[] generateReport(ReportData reportData) {
        try {
            ExcelReportGenerator generator = new ExcelReportGenerator(reportData);
            return generator.generate();
        } catch (RuntimeException e) {
            throw new ReportGenerationException(e);
        }
    }

}
