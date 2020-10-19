package de.cassisi.hearth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestHLMData {

    @Test
    void create() {

        HLMData hlmData = get();

    }

    private HLMData get() {
        return HLMData.builder()
                .eventList(new ArrayList<>())
                .bloodSamples(new ArrayList<>())
                .paramData(new ArrayList<>())
                .diagnosisData(new DiagnosisData(new ArrayList<>()))
                .operationData(new HlmOperationData(new ArrayList<>()))
                .riskFactorData(new RiskFactorData(new ArrayList<>()))
                .patientData(new PatientData(LocalDate.now(), PatientData.Sex.MALE, 32, "A", "pos", 190, 90, 2.1, 2.0, 2))
                .machineData(new MachineData("oxy", "h", "kanArt", "kanVen", "kanVen2"))
                .primingComposition(new PrimingComposition(200, new ArrayList<>()))
                .build();
    }

}
