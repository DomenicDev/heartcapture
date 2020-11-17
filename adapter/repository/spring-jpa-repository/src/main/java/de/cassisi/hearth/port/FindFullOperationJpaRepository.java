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
    public ResultData getOperationData(long operationId) {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));

        Operation operation = DBConverter.convert(operationDB);
        List<NIRSData> nirsData = DBConverter.convertNIRSData(operationDB.getNirsData());
        List<AnesthesiaData> anesthesiaData = DBConverter.convertAnesthesiaData(operationDB.getAnesthesiaData());

        return new ResultData(operation, nirsData, anesthesiaData);
    }
}
