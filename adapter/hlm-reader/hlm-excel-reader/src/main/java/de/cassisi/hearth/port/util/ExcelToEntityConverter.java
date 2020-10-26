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
        if ("Kardioplegie".equals(eventType)) return EventType.KARDIOPLEGIE;
        if ("Nabi 8,4%".equals(eventType)) return EventType.NABI_8_4_PC;
        if ("Jonosteril".equals(eventType)) return EventType.JONOSTERIL;
        if ("Heparin".equals(eventType)) return EventType.HEPARIN;
        if ("Haemofiltration".equals(eventType)) return EventType.HAEMOFILTRATION;
        if ("Aorta Zu".equals(eventType)) return EventType.AORTA_ZU;
        if ("Aorta Auf".equals(eventType)) return EventType.AORTA_AUF;
        if ("Reperfusion Beginn".equals(eventType)) return EventType.REPERFUSION_BEGINN;
        if ("Defibrillation".equals(eventType)) return EventType.DEFIBRILLATION;
        if ("Bypass Ende".equals(eventType)) return EventType.BYPASS_ENDE;
        if ("Reperfusion Ende".equals(eventType)) return EventType.REPERFUSION_ENDE;
        if ("Maschinenblut".equals(eventType)) return EventType.MASCHINENBLUT;
        if ("Restblut Perf,".equals(eventType)) return EventType.RESTBLUT_PERF;
        if ("Level Alarm Ein".equals(eventType)) return EventType.LEVEL_ALARM_EIN;
        if ("Level Alarm Aus".equals(eventType)) return EventType.LEVEL_ALARM_AUS;
        if ("Reservoirvolumen".equals(eventType)) return EventType.RESERVOIRVOLUMEN;
        if ("Cell-Saver abgesaugt".equals(eventType)) return EventType.CELL_SAVER_ABGESAUGT;
        if ("CS-EK".equals(eventType)) return EventType.CS_EK;
        if ("H\u00E4mofiltrat".equals(eventType)) return EventType.HAEMOFILTRAT;
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
                .hbOfl(data.getHbOfl())
                .hctOFL(data.getHctOfl())
                .phOfl(data.getPhOfl())
                .pco2Ofl(data.getPco2Ofl())
                .po2Ofl(data.getPo2Ofl())
                .hco3Ofl(data.getHco3Ofl())
                .tco2Ofl(data.getTco2Ofl())
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
                .kanuelArt(machineExcelData.getKanuelArt())
                .kanuelVen(machineExcelData.getKanuelVen())
                .kanuelVen2(machineExcelData.getKanuelVen2())
                .build();
    }

    public static PrimingComposition convertToPrimingComposition(PrimingExcelData excelData) {
        if (excelData == null) {
            // should not be the case
            return new PrimingComposition(0, new ArrayList<>());
        }
        List<Priming> primingData = new ArrayList<>();
        PrimingComposition primingComposition = new PrimingComposition(excelData.getTotalPriming(), primingData);
        
        if (isNotNullOrEmpty(excelData.getPriming1Text())) {
            primingData.add(new Priming(excelData.getPriming1Text(), excelData.getPriming1amount(), excelData.getPriming1Unit()));            
        }
        if (isNotNullOrEmpty(excelData.getPriming2Text())) {
            primingData.add(new Priming(excelData.getPriming2Text(), excelData.getPriming2amount(), excelData.getPriming2Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming3Text() )) {
            primingData.add(new Priming(excelData.getPriming3Text(), excelData.getPriming3amount(), excelData.getPriming3Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming4Text())) {
            primingData.add(new Priming(excelData.getPriming4Text(), excelData.getPriming4amount(), excelData.getPriming4Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming5Text())) {
            primingData.add(new Priming(excelData.getPriming5Text(), excelData.getPriming5amount(), excelData.getPriming5Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming6Text())) {
            primingData.add(new Priming(excelData.getPriming6Text(), excelData.getPriming6amount(), excelData.getPriming6Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming7Text())) {
            primingData.add(new Priming(excelData.getPriming7Text(), excelData.getPriming7amount(), excelData.getPriming7Unit()));
        }        
        if (isNotNullOrEmpty(excelData.getPriming8Text())) {
            primingData.add(new Priming(excelData.getPriming8Text(), excelData.getPriming8amount(), excelData.getPriming8Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming9Text())) {
            primingData.add(new Priming(excelData.getPriming9Text(), excelData.getPriming9amount(), excelData.getPriming9Unit()));
        }
        if (isNotNullOrEmpty(excelData.getPriming10Text())) {
            primingData.add(new Priming(excelData.getPriming10Text(), excelData.getPriming10amount(), excelData.getPriming10Unit()));
        }
        return primingComposition;
    }

    private static boolean isNotNullOrEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
