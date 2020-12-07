package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.*;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.GenerateReportRepository;
import de.cassisi.heartcapture.port.util.DBConverter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class GenerateReportJpaRepository implements GenerateReportRepository {

    private final OperationRepository operationRepository;

    public GenerateReportJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public ReportData getReportData(long operationId) {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));

        // gather data
        Operation operation = DBConverter.convert(operationDB);
        HLMData hlmData = DBConverter.convert(operationDB.getHlmData());
        List<NIRSData> nirsData = DBConverter.convertNIRSData(operationDB.getNirsData());
        List<InfusionData> infusionData = DBConverter.convertInfusionData(operationDB.getInfusionData());
        List<AnesthesiaData> anesthesiaData = DBConverter.convertAnesthesiaData(operationDB.getAnesthesiaData());

        return new ReportData(
                operation,
                hlmData,
                nirsData,
                infusionData,
                anesthesiaData);
    }
}
