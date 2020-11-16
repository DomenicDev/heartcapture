package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.FindFullOperationRepository;
import de.cassisi.hearth.util.DBConverter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class FindFullOperationJpaRepository implements FindFullOperationRepository {

    private final OperationRepository operationRepository;

    public FindFullOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public ReportData getFullOperationData(long operationId) {
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
