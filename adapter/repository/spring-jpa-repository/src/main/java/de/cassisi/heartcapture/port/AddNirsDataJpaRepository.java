package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.NIRSData;
import de.cassisi.heartcapture.port.repository.NirsRepository;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.NirsDataDB;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.AddNirsDataRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddNirsDataJpaRepository implements AddNirsDataRepository {

    private final NirsRepository nirsRepository;
    private final OperationRepository operationRepository;

    public AddNirsDataJpaRepository(NirsRepository nirsRepository, OperationRepository operationRepository) {
        this.nirsRepository = nirsRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public boolean isLocked(long operationId) throws OperationNotFoundException {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
        return operationDB.isLocked();
    }

    @Override
    @Transactional
    public void addNirsDataToOperation(long operationId, NIRSData nirsData) throws OperationNotFoundException {
        // check if operation does really exist
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));

        // convert to nirs database object and persist it
        NirsDataDB nirsDataDB = new NirsDataDB(
                null,
                nirsData.getTimestamp(),
                nirsData.getLeftSaturation(),
                nirsData.getRightSaturation(),
                operationDB);
        nirsRepository.save(nirsDataDB);

        // update specific operation entity
        operationDB.addNirsData(nirsDataDB);

        // set flag
        operationDB.setNirsDataAvailable(true);

        // save entity
        operationRepository.save(operationDB);
    }
}
