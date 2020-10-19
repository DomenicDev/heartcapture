package de.cassisi.hearth.util;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.repository.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDBConverter {

    @Test
    void convertHlmEventData() {
        // create test data
        LocalDateTime now = LocalDateTime.now();
        HLMEventData.Unit unit = HLMEventData.Unit.ML;
        HLMEventData.EventType eventType = HLMEventData.EventType.ACT;
        Integer amount = 2;
        String free = "Y";
        HLMEventData.Typ typ = HLMEventData.Typ.COM;

        // create entity object
        HLMEventData eventData = HLMEventData.builder()
                .timestamp(now)
                .typ(typ)
                .type(eventType)
                .amount(amount)
                .unit(unit)
                .free(free)
                .build();

        // convert to database object
        HlmEventDataDB db = DBConverter.convert(eventData);

        // validate
        assertEquals(now, db.getTimestamp());
        assertEquals(typ, db.getTyp());
        assertEquals(eventType, db.getEventType());
        assertEquals(amount, db.getAmount());
        assertEquals(free, db.getFree());
        assertEquals(unit, db.getUnit());

        // back to entity
        HLMEventData entity = DBConverter.convert(db);
        assertEquals(eventData.getTimestamp(), entity.getTimestamp());
        assertEquals(eventData.getTyp(), entity.getTyp());
        assertEquals(eventData.getType(), entity.getType());
        assertEquals(eventData.getAmount(), entity.getAmount());
        assertEquals(eventData.getUnit(), entity.getUnit());
        assertEquals(eventData.getFree(), entity.getFree());
    }

    @Test
    void convertHlmBloodSample() {
        LocalDateTime timestamp = LocalDateTime.now();
        HlmBloodSample bloodSample = new HlmBloodSample(
                timestamp,
                HlmBloodSample.Type.ART,
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                10, 11, 12, 13, 14);

        HlmBloodSampleDB db = DBConverter.convert(bloodSample);

        assertEquals(timestamp, db.getTimestamp());
        assertEquals(HlmBloodSample.Type.ART, db.getTyp());
        assertEquals(1, db.getHctOFL());
        assertEquals(2, db.getHbOfl());
        assertEquals(3, db.getPhOfl());
        assertEquals(4, db.getPco2Ofl());
        assertEquals(5, db.getPo2Ofl());
        assertEquals(6, db.getHco3Ofl());
        assertEquals(7, db.getTco2Ofl());
        assertEquals(8, db.getBavtOfl());
        assertEquals(9, db.getSo2Ofl());
        assertEquals(10, db.getGlucose());
        assertEquals(11, db.getNa());
        assertEquals(12, db.getK());
        assertEquals(13, db.getCa());
        assertEquals(14, db.getLactat());

        // back to entity
        HlmBloodSample entity = DBConverter.convert(db);
        assertEquals(bloodSample.getTimestamp(), entity.getTimestamp());
        assertEquals(bloodSample.getTyp(), entity.getTyp());
        assertEquals(bloodSample.getHctOFL(), entity.getHctOFL());
        assertEquals(bloodSample.getHbOfl(), entity.getHbOfl());
        assertEquals(bloodSample.getPhOfl(), entity.getPhOfl());
        assertEquals(bloodSample.getPco2Ofl(), entity.getPco2Ofl());
        assertEquals(bloodSample.getPo2Ofl(), entity.getPo2Ofl());
        assertEquals(bloodSample.getHco3Ofl(), entity.getHco3Ofl());
        assertEquals(bloodSample.getTco2Ofl(), entity.getTco2Ofl());
        assertEquals(bloodSample.getBavtOfl(), entity.getBavtOfl());
        assertEquals(bloodSample.getSo2Ofl(), entity.getSo2Ofl());
        assertEquals(bloodSample.getGlucose(), entity.getGlucose());
        assertEquals(bloodSample.getNa(), entity.getNa());
        assertEquals(bloodSample.getK(), entity.getK());
        assertEquals(bloodSample.getCa(), entity.getCa());
        assertEquals(bloodSample.getLactat(), entity.getLactat());
    }

    @Test
    void convertHlmParamData() {
        LocalDateTime timestamp = LocalDateTime.now();
        HlmParamData paramData = new HlmParamData(timestamp,
                1.,
                2.,
                3.,
                4.,
                5.,
                6.,
                7.,
                8.,
                9.,
                10.,
                11.,
                12.,
                13.,
                14.,
                15.,
                16.,
                17,
                18.,
                19,
                20,
                21,
                22.,
                23.,
                24.,
                25.,
                26.);

        HlmParamDataDB db = DBConverter.convert(paramData);

        assertEquals(timestamp, db.getTimestamp());
        assertEquals(1., db.getArtFlow());
        assertEquals(2., db.getFlow2());
        assertEquals(3., db.getFlow3());
        assertEquals(4., db.getPressure1());
        assertEquals(5., db.getPressure2());
        assertEquals(6., db.getTemp1());
        assertEquals(7., db.getTemp2());
        assertEquals(8., db.getTemp3());
        assertEquals(9., db.getPlegieflowA());
        assertEquals(10., db.getPlegieflowB());
        assertEquals(11., db.getPlegiedruck());
        assertEquals(12., db.getGasmixflow());
        assertEquals(13., db.getGasfio2());
        assertEquals(14., db.getBgtempVen());
        assertEquals(15., db.getO2satVen());
        assertEquals(16., db.getHct());
        assertEquals(17, db.getPathfreq());
        assertEquals(18, db.getPattemp1());
        assertEquals(19, db.getPatartdruck());
        assertEquals(20, db.getPatpuldruck());
        assertEquals(21, db.getPatzvdruck());
        assertEquals(22., db.getCi());
        assertEquals(23., db.getFlowRel());
        assertEquals(24., db.getSvr());
        assertEquals(25, db.getGasBlood());
        assertEquals(26, db.getNarkosegas());

        // back to entity
        HlmParamData entity = DBConverter.convert(db);
        assertEquals(paramData.getTimestamp(), entity.getTimestamp());
        assertEquals(paramData.getArtFlow(), entity.getArtFlow());
        assertEquals(paramData.getFlow2(), entity.getFlow2());
        assertEquals(paramData.getFlow3(), entity.getFlow3());
        assertEquals(paramData.getPressure1(), entity.getPressure1());
        assertEquals(paramData.getPressure2(), entity.getPressure2());
        assertEquals(paramData.getTemp1(), entity.getTemp1());
        assertEquals(paramData.getTemp2(), entity.getTemp2());
        assertEquals(paramData.getTemp3(), entity.getTemp3());
        assertEquals(paramData.getPlegieflowA(), entity.getPlegieflowA());
        assertEquals(paramData.getPlegieflowB(), entity.getPlegieflowB());
        assertEquals(paramData.getPlegiedruck(), entity.getPlegiedruck());
        assertEquals(paramData.getGasmixflow(), entity.getGasmixflow());
        assertEquals(paramData.getGasfio2(), entity.getGasfio2());
        assertEquals(paramData.getBgtempVen(), entity.getBgtempVen());
        assertEquals(paramData.getO2satVen(), entity.getO2satVen());
        assertEquals(paramData.getHct(), entity.getHct());
        assertEquals(paramData.getPathfreq(), entity.getPathfreq());
        assertEquals(paramData.getPattemp1(), entity.getPattemp1());
        assertEquals(paramData.getPatartdruck(), entity.getPatartdruck());
        assertEquals(paramData.getPatpuldruck(), entity.getPatpuldruck());
        assertEquals(paramData.getPatzvdruck(), entity.getPatzvdruck());
        assertEquals(paramData.getCi(), entity.getCi());
        assertEquals(paramData.getFlowRel(), entity.getFlowRel());
        assertEquals(paramData.getSvr(), entity.getSvr());
        assertEquals(paramData.getGasBlood(), entity.getGasBlood());
        assertEquals(paramData.getNarkosegas(), entity.getNarkosegas());
    }

    @Test
    void convertPatientData() {
        LocalDate birthday = LocalDate.now();
        PatientData patientData = new PatientData(birthday,
                PatientData.Sex.FEMALE,
                65,
                "A",
                "pos",
                190,
                90,
                2.1,
                2,
                3.2);

        PatientDataDB db = DBConverter.convert(patientData);

        assertEquals(birthday, db.getBirthday());
        assertEquals(PatientData.Sex.FEMALE, db.getSex());
        assertEquals(65, db.getAge());
        assertEquals("A", db.getBloodGroup());
        assertEquals("pos", db.getBloodRef());
        assertEquals(190, db.getHeight());
        assertEquals(90, db.getWeight());
        assertEquals(2.1, db.getBsa());
        assertEquals(2, db.getFactor());
        assertEquals(3.2, db.getSollFluss());

        // back to entity
        PatientData entity = DBConverter.convert(db);
        assertEquals(patientData.getBirthday(), entity.getBirthday());
        assertEquals(patientData.getAge(), entity.getAge());
        assertEquals(patientData.getBloodGroup(), entity.getBloodGroup());
        assertEquals(patientData.getBloodRef(), entity.getBloodRef());
        assertEquals(patientData.getHeight(), entity.getHeight());
        assertEquals(patientData.getWeight(), entity.getWeight());
        assertEquals(patientData.getBsa(), entity.getBsa());
        assertEquals(patientData.getFactor(), entity.getFactor());
        assertEquals(patientData.getSollFluss(), entity.getSollFluss());
    }

    @Test
    void convertMachineData() {
        MachineData machineData = new MachineData(
                "a",
                "b",
                "c",
                "d",
                "e");

        MachineDataDB db = DBConverter.convert(machineData);

        assertEquals("a", db.getOxygenator());
        assertEquals("b", db.getHaemoFil());
        assertEquals("c", db.getKanuelArt());
        assertEquals("d", db.getKanuelVen());
        assertEquals("e", db.getKanuelVen2());

        // back to entity
        MachineData entity = DBConverter.convert(db);
        assertEquals(machineData.getOxygenator(), entity.getOxygenator());
        assertEquals(machineData.getHaemoFil(), entity.getHaemoFil());
        assertEquals(machineData.getKanuelArt(), entity.getKanuelArt());
        assertEquals(machineData.getKanuelVen(), entity.getKanuelVen());
        assertEquals(machineData.getKanuelVen2(), entity.getKanuelVen2());
    }

    @Test
    void convertDiagnosisData() {
        List<String> diagnosis = Arrays.asList("First", "Second");
        DiagnosisData diagnosisData = new DiagnosisData(diagnosis);

        DiagnosisDataDB db = DBConverter.convert(diagnosisData);

        assertEquals(diagnosis.size(), db.getDiagnosisData().size());
        assertTrue(db.getDiagnosisData().containsAll(diagnosis));

        // back to entity
        DiagnosisData entity = DBConverter.convert(db);
        assertEquals(diagnosisData.getDiagnosisData().size(), entity.getDiagnosisData().size());
        assertTrue(diagnosisData.getDiagnosisData().containsAll(entity.getDiagnosisData()));
    }

    @Test
    void convertHlmOperationData() {
        List<String> operations = Arrays.asList("First", "Second", "Third");
        HlmOperationData operationData = new HlmOperationData(operations);

        HlmOperationDataDB db = DBConverter.convert(operationData);

        assertEquals(operations.size(), db.getPreviousOperations().size());
        assertTrue(db.getPreviousOperations().containsAll(operations));

        // back to entity
        HlmOperationData entity = DBConverter.convert(db);
        assertEquals(operationData.getOperation().size(), entity.getOperation().size());
        assertTrue(operationData.getOperation().containsAll(entity.getOperation()));
    }

    @Test
    void convertRiskFactorData() {
        List<String> risks = Arrays.asList("First", "Second", "Third");
        RiskFactorData riskFactorData = new RiskFactorData(risks);

        RiskFactorDataDB db = DBConverter.convert(riskFactorData);

        assertEquals(risks.size(), db.getRiskFactors().size());
        assertTrue(db.getRiskFactors().containsAll(risks));

        // back to entity
        RiskFactorData entity = DBConverter.convert(db);
        assertEquals(riskFactorData.getRisks().size(), entity.getRisks().size());
        assertTrue(riskFactorData.getRisks().containsAll(entity.getRisks()));
    }

    @Test
    void convertPrimingComposition() {
        List<Priming> primings = Arrays.asList(new Priming("a", 1, "ml"), new Priming("b", 2, "sec"));
        PrimingComposition primingComposition = new PrimingComposition(3, primings);

        PrimingCompositionDB db = DBConverter.convert(primingComposition);

        assertEquals(primings.size(), db.getPrimingData().size());
        assertEquals(primingComposition.getTotalPriming(), db.getTotalPriming());

        // back to entity
        PrimingComposition entity = DBConverter.convert(db);
        assertEquals(primingComposition.getTotalPriming(), entity.getTotalPriming());
        assertEquals(primingComposition.getPrimingData().size(), entity.getPrimingData().size());
    }


    @Test
    void convertPriming() {
        Priming priming = new Priming("a", 2, "ml");

        // to database
        PrimingDataDB db = DBConverter.convert(priming);

        assertEquals("a", db.getText());
        assertEquals(2, db.getAmount());
        assertEquals("ml", db.getUnit());

        // back to entity
        DBConverter.convert(db);
    }

    @Test
    void convertOperation() {
        LocalDate date = LocalDate.now();
        OperationDB operationDB =  OperationDB.builder()
                .id(1L)
                .date(date)
                .roomNr("Room 4")
                .nirsDataAvailable(true)
                .infusionDataAvailable(true)
                .anesthesiaDataAvailable(false)
                .hlmDataAvailable(false)
                .locked(false)
                .build();

        Operation entity = DBConverter.convert(operationDB);
        assertEquals(operationDB.getId(), entity.getId());
        assertEquals(operationDB.getDate(), entity.getDate());
        assertEquals(operationDB.getRoomNr(), entity.getRoomNr());
        assertEquals(operationDB.isNirsDataAvailable(), entity.isNirsDataAvailable());
        assertEquals(operationDB.isAnesthesiaDataAvailable(), entity.isAnesthesiaDataAvailable());
        assertEquals(operationDB.isInfusionDataAvailable(), entity.isInfusionDataAvailable());
        assertEquals(operationDB.isHlmDataAvailable(), entity.isHlmDataAvailable());
        assertEquals(operationDB.isLocked(), entity.isLocked());
    }

    @Test
    void convertAnesthesiaData() {
        AnesthesiaDataDB db = new AnesthesiaDataDB(1L, LocalDateTime.now(), 34.2, null);
        AnesthesiaData entity = DBConverter.convert(db);
        assertEquals(db.getDepthOfAnesthesia(), entity.getDepthOfAnesthesia());
        assertEquals(db.getTimestamp(), entity.getTimestamp());

        List<AnesthesiaDataDB> dbList = Collections.singletonList(db);
        List<AnesthesiaData> anesthesiaData = DBConverter.convertAnesthesiaData(dbList);
        assertEquals(dbList.size(), anesthesiaData.size());
        assertEquals(dbList.get(0).getTimestamp(), anesthesiaData.get(0).getTimestamp());
        assertEquals(dbList.get(0).getDepthOfAnesthesia(), anesthesiaData.get(0).getDepthOfAnesthesia());
    }

    @Test
    void convertPerfusorData() {
        PerfusorDataDB db = new PerfusorDataDB(1L, "test_name", 3.2);

        PerfusorData entity = DBConverter.convert(db);
        assertEquals(db.getInfusionName(), entity.getName());
        assertEquals(db.getRate(), entity.getRate());
    }

    @Test
    void convertInfusionData() {
        InfusionDataDB db = new InfusionDataDB(1L, LocalDateTime.now(), null);
        InfusionData entity = DBConverter.convert(db);
        assertEquals(db.getTimestamp(), entity.getTimestamp());
    }

    @Test
    void convertNIRSData() {
        NirsDataDB db = new NirsDataDB(1L, LocalDateTime.now(), 48, 59, null);

        NIRSData entity = DBConverter.convert(db);
        assertEquals(db.getLeftSaturation(), entity.getLeftSaturation());
        assertEquals(db.getRightSaturation(), entity.getRightSaturation());
        assertEquals(db.getTimestamp(), entity.getTimestamp());
    }

}
