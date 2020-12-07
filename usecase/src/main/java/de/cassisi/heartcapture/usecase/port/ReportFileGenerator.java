package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.ReportData;
import de.cassisi.heartcapture.usecase.exception.ReportGenerationException;

public interface ReportFileGenerator {

    byte[] generateReport(ReportData reportData) throws ReportGenerationException;

}
