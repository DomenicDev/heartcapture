package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.usecase.exception.ReportGenerationException;

import java.util.List;

public interface ReportFileGenerator {

    byte[] generateReport(ReportData reportData) throws ReportGenerationException;

}
