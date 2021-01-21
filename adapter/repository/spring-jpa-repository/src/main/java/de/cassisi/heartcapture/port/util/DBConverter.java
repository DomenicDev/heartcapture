package de.cassisi.heartcapture.port.util;

import de.cassisi.heartcapture.entity.*;
import de.cassisi.heartcapture.repository.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Converts Entity object to the corresponding database objects.
 */
public final class DBConverter {

    private static final ModelMapper mapper = new ModelMapper();
    private static final  ModelMapper builderMapper = new ModelMapper();

    static {
        builderMapper.getConfiguration().setDestinationNamingConvention(NamingConventions.builder());
        builderMapper.addConverter(context -> builderMapper.map(context.getSource(), Priming.PrimingBuilder.class).build(), PrimingDataDB.class, Priming.class);
    }

    // ************** ENTITY TO DB ****************** //

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

    public static DiagnosisDataDB convert(DiagnosisData diagnosisData) {
        DiagnosisDataDB diagnosisDataDB = new DiagnosisDataDB();
        diagnosisDataDB.getDiagnosisData().addAll(diagnosisData.getDiagnosisData());
        return diagnosisDataDB;
    }

    public static HlmOperationDataDB convert(HlmOperationData hlmOperationData) {
        HlmOperationDataDB hlmOperationDataDB = new HlmOperationDataDB();
        hlmOperationDataDB.getPreviousOperations().addAll(hlmOperationData.getOperation());
        return hlmOperationDataDB;
    }

    public static RiskFactorDataDB convert(RiskFactorData riskFactorData) {
        RiskFactorDataDB riskFactorDataDB = new RiskFactorDataDB();
        riskFactorDataDB.getRiskFactors().addAll(riskFactorData.getRisks());
        return riskFactorDataDB;
    }

    public static PrimingCompositionDB convert(PrimingComposition primingComposition) {
        PrimingCompositionDB db = new PrimingCompositionDB();
        db.setTotalPriming(primingComposition.getTotalPriming());
        List<PrimingDataDB> primingDataDBList = new ArrayList<>();
        primingComposition.getPrimingData().forEach(data -> primingDataDBList.add(convert(data)));
        db.getPrimingData().addAll(primingDataDBList);
        return db;
    }

    public static PrimingDataDB convert(Priming priming) {
        return mapper.map(priming, PrimingDataDB.class);
    }

    //___________________________________________________//
    // ******************* DB TO ENTITY **************** //

    public static Operation convert(OperationDB operationDB) {
        return new Operation(
                operationDB.getId(),
                operationDB.getDate(),
                operationDB.getRoomNr(),
                operationDB.isNirsDataAvailable(),
                operationDB.isInfusionDataAvailable(),
                operationDB.isAnesthesiaDataAvailable(),
                operationDB.isHlmDataAvailable(),
                operationDB.isLocked()
        );
    }

    public static HLMData convert(HLMDataDB hlmDataDB) {
        List<HLMEventData> eventData = convertEventData(hlmDataDB.getHlmEventDataDBList());
        List<HlmBloodSample> bloodSamples = convertBloodSamples(hlmDataDB.getHlmBloodSampleDBList());
        List<HlmParamData> paramData = convertParamData(hlmDataDB.getHlmParamDataDBS());

        DiagnosisData diagnosisData = convert(hlmDataDB.getDiagnosisDataDB());
        HlmOperationData hlmOperationData = convert(hlmDataDB.getHlmOperationDataDB());
        RiskFactorData riskFactorData = convert(hlmDataDB.getRiskFactorDataDB());
        PatientData patientData = convert(hlmDataDB.getPatientDataDB());
        MachineData machineData = convert(hlmDataDB.getMachineDataDB());
        PrimingComposition primingComposition = convert(hlmDataDB.getPrimingCompositionDB());

        return HLMData.builder()
                .eventList(eventData)
                .bloodSamples(bloodSamples)
                .paramData(paramData)
                .diagnosisData(diagnosisData)
                .operationData(hlmOperationData)
                .riskFactorData(riskFactorData)
                .patientData(patientData)
                .machineData(machineData)
                .primingComposition(primingComposition)
                .build();
    }

    public static PrimingComposition convert(PrimingCompositionDB primingCompositionDB) {
        return builderMapper.map(primingCompositionDB, PrimingComposition.PrimingCompositionBuilder.class).build();
    }

    public static Priming convert(PrimingDataDB primingDataDB) {
        return mapper.map(primingDataDB, Priming.PrimingBuilder.class).build();
    }

    public static MachineData convert(MachineDataDB machineDataDB) {
        return builderMapper.map(machineDataDB, MachineData.MachineDataBuilder.class).build();
    }

    public static PatientData convert(PatientDataDB patientDataDB) {
        return builderMapper.map(patientDataDB, PatientData.PatientDataBuilder.class).build();
    }

    public static RiskFactorData convert(RiskFactorDataDB riskFactorDataDB) {
        return new RiskFactorData(riskFactorDataDB.getRiskFactors());
    }

    public static HlmOperationData convert(HlmOperationDataDB hlmOperationDataDB) {
        return new HlmOperationData(hlmOperationDataDB.getPreviousOperations());
    }

    public static List<HlmBloodSample> convertBloodSamples(List<HlmBloodSampleDB> hlmBloodSampleDBList) {
        List<HlmBloodSample> bloodSamples = new ArrayList<>();
        hlmBloodSampleDBList.forEach(sample -> bloodSamples.add(convert(sample)));
        return bloodSamples;
    }

    public static HlmBloodSample convert(HlmBloodSampleDB bloodSampleDB) {
        return builderMapper.map(bloodSampleDB, HlmBloodSample.HlmBloodSampleBuilder.class).build();
    }

    public static HLMEventData convert(HlmEventDataDB eventDataDB) {
        return builderMapper.map(eventDataDB, HLMEventData.HLMEventDataBuilder.class).build();
    }

    public static List<HLMEventData> convertEventData(List<HlmEventDataDB> hlmEventDataDBList) {
        List<HLMEventData> eventDataList = new ArrayList<>();
        hlmEventDataDBList.forEach(event -> eventDataList.add(convert(event)));
        return eventDataList;
    }

    public static List<HlmParamData> convertParamData(List<HlmParamDataDB> paramDataDB) {
        List<HlmParamData> paramData = new ArrayList<>();
        paramDataDB.forEach(data -> paramData.add(convert(data)));
        return paramData;
    }

    public static HlmParamData convert(HlmParamDataDB data) {
        return builderMapper.map(data, HlmParamData.HlmParamDataBuilder.class).build();
    }

    public static DiagnosisData convert(DiagnosisDataDB diagnosisDataDB) {
        return new DiagnosisData(diagnosisDataDB.getDiagnosisData());
    }

    public static List<NIRSData> convertNIRSData(Set<NirsDataDB> nirsDataDB) {
        List<NIRSData> nirsData = new ArrayList<>();
        nirsDataDB.forEach(data -> nirsData.add(convert(data)));
        return nirsData;
    }

    public static NIRSData convert(NirsDataDB data) {
        return new NIRSData(data.getTimestamp(), data.getLeftSaturation(), data.getRightSaturation());
    }


    public static List<InfusionData> convertInfusionData(Set<InfusionDataDB> infusionDataDB) {
        List<InfusionData> infusionData = new ArrayList<>();
        infusionDataDB.forEach(data -> infusionData.add(convert(data)));
        return infusionData;
    }

    public static InfusionData convert(InfusionDataDB data) {
        return new InfusionData(data.getTimestamp(), convertPerfusorData(data.getInfusionData()));
    }

    public static List<PerfusorData> convertPerfusorData(List<PerfusorDataDB> infusionData) {
        List<PerfusorData> perfusorData = new ArrayList<>();
        infusionData.forEach(data -> perfusorData.add(convert(data)));
        return perfusorData;
    }

    public static PerfusorData convert(PerfusorDataDB data) {
        return new PerfusorData(data.getInfusionName(), data.getRate());
    }

    public static List<AnesthesiaData> convertAnesthesiaData(Collection<AnesthesiaDataDB> anesthesiaDataDB) {
        List<AnesthesiaData> anesthesiaData = new ArrayList<>();
        anesthesiaDataDB.forEach(data -> anesthesiaData.add(convert(data)));
        return anesthesiaData;
    }

    public static AnesthesiaData convert(AnesthesiaDataDB data) {
        return new AnesthesiaData(data.getTimestamp(), data.getDepthOfAnesthesia());
    }

    public static PreMedicationDataDB convert(PreMedicationData data) {
        return mapper.map(data, PreMedicationDataDB.class);
    }

    public static PreMedicationData convert(PreMedicationDataDB data) {
        return new PreMedicationData(
                data.getSuprareninPreOperation(),
                data.getNorepinephrinPreOperation(),
                data.getVasopressinPreOperation(),
                data.getMilrinonPreOperation(),
                data.getNtgPreOperation(),
                data.getSimdaxPreOperation(),
                data.getHeparinPreOperation(),
                data.getSuprareninPreHlm(),
                data.getNorepinephrinPreHlm(),
                data.getVasopressinPreHlm(),
                data.getMilrinonPreHlm(),
                data.getNtgPreHlm(),
                data.getSimdaxPreHlm()
        );
    }


}
