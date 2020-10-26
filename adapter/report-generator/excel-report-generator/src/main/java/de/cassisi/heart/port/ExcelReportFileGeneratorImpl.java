package de.cassisi.heart.port;

import de.cassisi.hearth.entity.ReportData;
import de.cassisi.hearth.usecase.exception.ReportGenerationException;
import de.cassisi.hearth.usecase.port.ReportFileGenerator;

public class ExcelReportFileGeneratorImpl implements ReportFileGenerator {

    @Override
    public byte[] generateReport(ReportData reportData) {
        try {
            ExcelReportGenerator generator = new ExcelReportGenerator();
            return generator.generate(reportData);
        } catch (RuntimeException e) {
            throw new ReportGenerationException(e);
        }
    }

}
