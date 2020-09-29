package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.repository.NirsRepository;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.NirsDataDB;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.AddNirsDataRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AddNirsDataJpaRepository implements AddNirsDataRepository {

    private NirsRepository nirsRepository;
    private OperationRepository operationRepository;


    public AddNirsDataJpaRepository(NirsRepository nirsRepository, OperationRepository operationRepository) {
        this.nirsRepository = nirsRepository;
        this.operationRepository = operationRepository;
    }


    @Transactional
    @Override
    public void addNirsDataToOperation(long operationId, NIRSData nirsData) {
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
        operationRepository.save(operationDB);
    }
}
