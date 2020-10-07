package de.cassisi.hearth.port.util;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.entity.HLMEventData.EventType;
import de.cassisi.hearth.port.data.*;

import java.util.ArrayList;
import java.util.List;

public final class ExcelToEntityConverter {

    public static List<HLMEventData> convertToEventData(List<EventExcelData> data) {
        List<HLMEventData> result = new ArrayList<>();
        data.forEach(event -> result.add(convertBloodSampleType(event)));
        return result;
    }

    public static List<HlmParamData> convertToParamData(List<HLMParamsExcelData> params) {
        List<HlmParamData> result = new ArrayList<>();
        params.forEach(data -> result.add(convertBloodSampleType(data)));
        return result;
    }

    public static DiagnosisData convertToDiagnosisData(List<DiagnosisExcelData> diagnosisExcelData) {
        List<String> diagnosisList = new ArrayList<>();
        diagnosisExcelData.forEach(data -> diagnosisList.add(data.getDiagnosis()));
        return new DiagnosisData(diagnosisList);
    }

    public static List<HlmBloodSample> convertToBloodSampleData(List<BloodSampleExcelData> bloodSampleExcelData) {
        List<HlmBloodSample> result = new ArrayList<>();
        bloodSampleExcelData.forEach(data -> result.add(convertBloodSampleType(data)));
        return result;
    }

    public static HlmOperationData convertToOperationData(List<OperationExcelData> operationExcelData) {
        List<String> operationList = new ArrayList<>();
        operationExcelData.forEach(data -> operationList.add(data.getOperation()));
        return new HlmOperationData(operationList);
    }

    public static RiskFactorData convertToRiskFactorData(List<RiskFactorsExcelData> riskFactorsExcelData) {
        List<String> risks = new ArrayList<>();
        riskFactorsExcelData.forEach(data -> risks.add(data.getRiskFactor()));
        return new RiskFactorData(risks);
    }

    public static PatientData convertToPatientData(PatientExcelData patientExcelData, PatientAdditionalExcelData patientAdditionalExcelData) {
        return PatientData.builder()
                .birthday(patientExcelData.getBirthday())
                .sex(convertSex(patientExcelData.getSex()))
                .age(patientExcelData.getAge())
                .bloodGroup(patientAdditionalExcelData.getBloodGroup())
                .bloodRef(patientAdditionalExcelData.getBloodRF())
                .height(patientAdditionalExcelData.getHeight())
                .weight(patientAdditionalExcelData.getWeight())
                .bsa(patientAdditionalExcelData.getBsa())
                .factor(patientAdditionalExcelData.getFactor())
                .sollFluss(patientAdditionalExcelData.getSollFluss())
                .build();
    }

    private static EventType convertToEventType(String eventType) {
        if (eventType == null) return EventType.UNKNOWN;
        if ("ACT".equals(eventType)) return EventType.ACT;
        if ("Bypass Beginn".equals(eventType)) return EventType.BYPASS_BEGINN;
        if ("Aorta Zu".equals(eventType)) return EventType.AORTA_ZU;
        if ("Kardioplegie".equals(eventType)) return EventType.KARDIOPLEGIE;
        if ("Nabi 8,4%".equals(eventType)) return EventType.NABI_8_4_PC;
        if ("Jonosteril".equals(eventType)) return EventType.JONOSTERIL;
        if ("Aorta Auf".equals(eventType)) return EventType.AORTA_AUF;
        if ("Reperfusion Beginn".equals(eventType)) return EventType.REPERFUSION_BEGINN;
        if ("Defibrillation".equals(eventType)) return EventType.DEFIBRILLATION;
        if ("Bypass Ende".equals(eventType)) return EventType.BYPASS_ENDE;
        if ("Reperfusion Ende".equals(eventType)) return EventType.REPERFUSION_ENDE;
        if ("Maschinenblut".equals(eventType)) return EventType.MASCHINENBLUT;
        if ("Restblut Perf,".equals(eventType)) return EventType.RESTBLUT_PERF;
        return EventType.UNKNOWN;
    }

    private static HLMEventData.Unit convertToUnit(String unit) {
        if ("ml".equals(unit)) {
            return HLMEventData.Unit.ML;
        }
        if ("sec".equals(unit)) {
            return HLMEventData.Unit.SEC;
        }
        return null;
    }

    private static HLMEventData.Typ convertToTyp(String typ) {
        if ("com".equals(typ)) return HLMEventData.Typ.COM;
        if ("in".equals(typ)) return HLMEventData.Typ.IN;
        if ("out".equals(typ)) return HLMEventData.Typ.OUT;
        return null;
    }

    public static HlmParamData convertBloodSampleType(HLMParamsExcelData data) {
        return HlmParamData.builder()
                .timestamp(data.getTimestamp())
                .artFlow(data.getArtFlow())
                .flow2(data.getFlow2())
                .flow3(data.getFlow3())
                .pressure1(data.getPressure1())
                .pressure2(data.getPressure2())
                .temp1(data.getTemp1())
                .temp2(data.getTemp2())
                .temp3(data.getTemp3())
                .plegieflowA(data.getPlegieflowA())
                .plegieflowB(data.getPlegieflowB())
                .plegiedruck(data.getPlegiedruck())
                .gasmixflow(data.getGasmixflow())
                .gasfio2(data.getGasfio2())
                .bgtempVen(data.getBgtempVen())
                .o2satVen(data.getO2satVen())
                .hct(data.getHct())
                .pathfreq(data.getPathfreq())
                .pattemp1(data.getPattemp1())
                .patartdruck(data.getPatartdruck())
                .patpuldruck(data.getPatpuldruck())
                .patzvdruck(data.getPatzvdruck())
                .ci(data.getCi())
                .flowRel(data.getFlowRel())
                .svr(data.getSvr())
                .gasBlood(data.getGasBlood())
                .narkosegas(data.getNarkosegas())
                .build();
    }

    public static HLMEventData convertBloodSampleType(EventExcelData data) {
        return new HLMEventData(
                data.getTimestamp(),
                convertToEventType(data.getText()),
                data.getAmount(),
                convertToUnit(data.getUnit()),
                data.getFactor(),
                data.getFree(),
                convertToTyp(data.getType())
        );
    }


    public static HlmBloodSample convertBloodSampleType(BloodSampleExcelData data) {
        return HlmBloodSample.builder()
                .timestamp(data.getTimestamp())
                .typ(convertBloodSampleType(data.getTyp()))
                .hctOFL(data.getHctOfl())
                .phOfl(data.getPhOfl())
                .pco2Ofl(data.getPco2Ofl())
                .po2Ofl(data.getPo2Ofl())
                .hco3Ofl(data.getHco3Ofl())
                .bavtOfl(data.getBavtOfl())
                .so2Ofl(data.getSo2Ofl())
                .glucose(data.getGlucose())
                .na(data.getNa())
                .k(data.getK())
                .ca(data.getCa())
                .lactat(data.getLactat())
                .build();
    }

    private static HlmBloodSample.Type convertBloodSampleType(String type) {
        if ("art".equals(type)) return HlmBloodSample.Type.ART;
        if ("ven".equals(type)) return HlmBloodSample.Type.VEN;
        throw new IllegalArgumentException("specified type must be either 'art' or 'ven'. Value: " + type);
    }



    private static PatientData.Sex convertSex(String sex) {
        if ("m\u00e4nnlich".equals(sex)) return PatientData.Sex.MALE;
        if ("weiblich".equals(sex)) return PatientData.Sex.FEMALE;
        return null;
    }

    public static MachineData convertToMachineData(MachineExcelData machineExcelData) {
        return MachineData.builder()
                .haemoFil(machineExcelData.getHaemoFilter())
                .oxygenator(machineExcelData.getOxygenator())
                .build();
    }
}
