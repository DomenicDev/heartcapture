package de.cassisi.hearth.entity;

import lombok.*;

import java.util.List;

import static java.util.Collections.unmodifiableList;

@Getter
@Builder
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

    public HLMData(@NonNull List<HLMEventData> eventList,
                   @NonNull List<HlmBloodSample> bloodSamples,
                   @NonNull List<HlmParamData> paramData,
                   @NonNull DiagnosisData diagnosisData,
                   @NonNull HlmOperationData operationData,
                   @NonNull RiskFactorData riskFactorData,
                   @NonNull PatientData patientData,
                   @NonNull MachineData machineData,
                   @NonNull PrimingComposition primingComposition) {
        this.eventList = unmodifiableList(eventList);
        this.bloodSamples = unmodifiableList(bloodSamples);
        this.paramData = unmodifiableList(paramData);
        this.diagnosisData = diagnosisData;
        this.operationData = operationData;
        this.riskFactorData = riskFactorData;
        this.patientData = patientData;
        this.machineData = machineData;
        this.primingComposition = primingComposition;
    }
}
