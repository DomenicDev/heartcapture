package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.AnesthesiaData;
import de.cassisi.heartcapture.entity.NIRSData;
import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.FindFullOperationRepository;
import de.cassisi.heartcapture.port.util.DBConverter;
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
    public ResultData getOperationData(long operationId) {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));

        Operation operation = DBConverter.convert(operationDB);
        List<NIRSData> nirsData = DBConverter.convertNIRSData(operationDB.getNirsData());
        List<AnesthesiaData> anesthesiaData = DBConverter.convertAnesthesiaData(operationDB.getAnesthesiaData());

        return new ResultData(operation, nirsData, anesthesiaData);
    }
}
