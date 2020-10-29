package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.repository.HLMRepository;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.HLMDataDB;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.ReadHLMDataFileRepository;
import de.cassisi.hearth.util.DBConverter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static de.cassisi.hearth.util.DBConverter.*;

@Repository
public class ReadHLMDataFileJpaRepository implements ReadHLMDataFileRepository {

    private HLMRepository hlmRepository;
    private OperationRepository operationRepository;

    public ReadHLMDataFileJpaRepository(HLMRepository hlmRepository, OperationRepository operationRepository) {
        this.hlmRepository = hlmRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public void saveHLMData(long operationId, HLMData data) {
        OperationDB operationDB = operationRepository.findById(operationId)
                .orElseThrow(() -> new OperationNotFoundException(operationId));

        HLMDataDB hlmDataDB = new HLMDataDB();

        // process event data
        List<HLMEventData> entityEventData = data.getEventList();
        entityEventData.forEach(entity -> hlmDataDB.add(convert(entity)));

        // process blood samples
        List<HlmBloodSample> entityBloodSample = data.getBloodSamples();
        entityBloodSample.forEach(entity -> hlmDataDB.add(convert(entity)));

        // process param data
        List<HlmParamData> hlmParamData = data.getParamData();
        hlmParamData.forEach(entity -> hlmDataDB.add(convert(entity)));

        // process diagnosis data
        hlmDataDB.setDiagnosisDataDB(convert(data.getDiagnosisData()));

        // process previous operation data
        hlmDataDB.setHlmOperationDataDB(convert(data.getOperationData()));

        // process risks
        hlmDataDB.setRiskFactorDataDB(convert(data.getRiskFactorData()));

        // process patient data
        hlmDataDB.setPatientDataDB(convert(data.getPatientData()));

        // process machine data
        hlmDataDB.setMachineDataDB(convert(data.getMachineData()));

        // process priming data
        hlmDataDB.setPrimingCompositionDB(convert(data.getPrimingComposition()));

        operationDB.setHlmData(hlmDataDB);

        // set flag
        operationDB.setHlmDataAvailable(true);

        operationRepository.save(operationDB);
    }

    @Override
    public Operation getOperation(long operationId) {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
        return DBConverter.convert(operationDB);

    }
}
