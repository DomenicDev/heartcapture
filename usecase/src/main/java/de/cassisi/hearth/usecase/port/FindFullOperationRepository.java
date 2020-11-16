package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.ReportData;

public interface FindFullOperationRepository {

    ReportData getFullOperationData(long operationId);

}
