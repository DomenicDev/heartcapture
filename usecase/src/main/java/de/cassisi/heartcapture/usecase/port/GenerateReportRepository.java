package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.ReportData;
import de.cassisi.heartcapture.usecase.exception.MissingHlmFileException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface GenerateReportRepository {

    ReportData getReportData(long operationId) throws OperationNotFoundException, MissingHlmFileException;
}
