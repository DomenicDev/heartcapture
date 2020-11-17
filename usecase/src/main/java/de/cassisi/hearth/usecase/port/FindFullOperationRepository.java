package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.entity.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public interface FindFullOperationRepository {

    ResultData getOperationData(long operationId);

    @Getter
    @AllArgsConstructor
    class ResultData {
        private final Operation operation;
        private final List<NIRSData> nirsData;
        private final List<AnesthesiaData> anesthesiaData;
    }

}
