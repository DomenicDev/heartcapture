package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.ReportDocument;
import de.cassisi.hearth.entity.ids.OperationId;

public interface ReportRepository {

    void store(OperationId operationId, ReportDocument reportDocument);

    ReportDocument findByOperation(OperationId operationId);

}
