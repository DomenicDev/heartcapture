package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public final class HLMData {

    private final List<HLMEventData> eventList;
    private final List<HlmBloodSample> bloodSamples;
    private final List<HlmParamData> paramData;

    private final DiagnosisData diagnosisData;
    private final HlmOperationData operationData;
    private final RiskFactorData riskFactorData;
    private final PatientData patientData;
    private final MachineData machineData;
    private final PrimingComposition primingComposition;

}
