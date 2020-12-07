package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.AnesthesiaData;
import de.cassisi.heartcapture.port.repository.AnesthesiaRepository;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.AnesthesiaDataDB;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.AddAnesthesiaDataRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AddAnesthesiaDataJpaRepository implements AddAnesthesiaDataRepository {

    private final AnesthesiaRepository anesthesiaRepository;
    private final OperationRepository operationRepository;

    public AddAnesthesiaDataJpaRepository(AnesthesiaRepository anesthesiaRepository, OperationRepository operationRepository) {
        this.anesthesiaRepository = anesthesiaRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public boolean isLocked(long operationId) throws OperationNotFoundException {
        return false;
    }

    @Override
    @Transactional
    public void addAnesthesiaData(long operationId, AnesthesiaData anesthesiaData) throws OperationNotFoundException {
        // find operation to add the anesthesia data to
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));

        // build database object
        AnesthesiaDataDB anesthesiaDataDB = AnesthesiaDataDB.builder()
                .depthOfAnesthesia(anesthesiaData.getDepthOfAnesthesia())
                .operation(operationDB)
                .timestamp(anesthesiaData.getTimestamp())
                .build();

        // save entity to database
        anesthesiaRepository.save(anesthesiaDataDB);

        // set flag
        operationDB.setAnesthesiaDataAvailable(true);
    }
}
