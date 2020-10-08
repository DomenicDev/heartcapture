package de.cassisi.hearth.util;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.repository.model.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Entity object to the corresponding database objects.
 */
public final class DBConverter {

    private static ModelMapper mapper = new ModelMapper();

    public static HlmEventDataDB convert(HLMEventData data) {
        return mapper.map(data, HlmEventDataDB.class);
    }

    public static HlmBloodSampleDB convert(HlmBloodSample entity) {
        return mapper.map(entity, HlmBloodSampleDB.class);
    }

    public static HlmParamDataDB convert(HlmParamData entity) {
        return mapper.map(entity, HlmParamDataDB.class);
    }

    public static PatientDataDB convert(PatientData patientData) {
        return mapper.map(patientData, PatientDataDB.class);
    }

    public static MachineDataDB convert(MachineData machineData) {
        return mapper.map(machineData, MachineDataDB.class);
    }

    public static PrimingCompositionDB convert(PrimingComposition primingComposition) {
        PrimingCompositionDB db = new PrimingCompositionDB();
        db.setTotalPriming(primingComposition.getTotalPriming());
        List<PrimingDataDB> primingDataDBList = new ArrayList<>();
        primingComposition.getPrimingData().forEach(data -> primingDataDBList.add(mapper.map(data, PrimingDataDB.class)));
        db.getPrimingDataDBList().addAll(primingDataDBList);
        return db;
    }
}
