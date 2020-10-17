package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.*;

import java.util.List;

public interface GenerateReportRepository {

    ReportData getReportData(long operationId);
}
