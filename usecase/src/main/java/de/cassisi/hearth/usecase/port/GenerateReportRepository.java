package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.usecase.exception.MissingHlmFileException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;

import java.util.List;

public interface GenerateReportRepository {

    ReportData getReportData(long operationId) throws OperationNotFoundException, MissingHlmFileException;
}
