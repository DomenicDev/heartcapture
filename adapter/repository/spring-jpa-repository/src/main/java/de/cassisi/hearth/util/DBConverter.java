package de.cassisi.hearth.util;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.repository.model.*;
import org.modelmapper.ModelMapper;

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
}
