package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.repository.InfusionRepository;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.PerfusorRepository;
import de.cassisi.hearth.repository.model.InfusionDataDB;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.repository.model.PerfusorDataDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.AddInfusionDataRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AddInfusionDataJpaRepository implements AddInfusionDataRepository {

    private final PerfusorRepository perfusorRepository;
    private final InfusionRepository infusionRepository;
    private final OperationRepository operationRepository;

    public AddInfusionDataJpaRepository(PerfusorRepository perfusorRepository, InfusionRepository infusionRepository, OperationRepository operationRepository) {
        this.perfusorRepository = perfusorRepository;
        this.infusionRepository = infusionRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public void addInfusionDataToOperation(long operationId, InfusionData infusionData) throws OperationNotFoundException {
        if (!operationRepository.existsById(operationId)) {
            throw new OperationNotFoundException(operationId);
        }

        OperationDB operationDB = operationRepository.findById(operationId).get();

        // convert all perfusor entities to equivalent database objects
        List<PerfusorDataDB> perfusorDataList = new ArrayList<>();
        infusionData.getPerfusorDataList().forEach((data) -> {
            PerfusorDataDB perfusorDataDB = PerfusorDataDB.builder()
                    .infusionName(data.getName())
                    .rate(data.getRate())
                    .build();
            perfusorRepository.save(perfusorDataDB);
            perfusorDataList.add(perfusorDataDB);
        });

        // convert to infusion database entity
        InfusionDataDB infusionDataDB = InfusionDataDB.builder()
                .timestamp(infusionData.getTimestamp())
                .operation(operationDB)
                .build();

        // add perfusor data to infusion data set
        perfusorDataList.forEach(infusionDataDB::addPerfusorData);

        // save infusion data
        infusionDataDB = infusionRepository.save(infusionDataDB);

        // add infusion data to operation and save it
        operationDB.getInfusionData().add(infusionDataDB);

        // set flag
        operationDB.setInfusionDataAvailable(true);

        operationRepository.save(operationDB);
    }

    @Override
    public boolean isLocked(long operationId) throws OperationNotFoundException{
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
        return operationDB.isLocked();
    }
}
