package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;

import java.util.List;

public interface GenerateReportRepository {

    HLMData findHLMData(long operationId);

    List<InfusionData> findInfusionData(long operationId);

    List<AnesthesiaData> findAnesthesiaData(long operationId);

    List<NIRSData> findNirsData(long operationId);


}
