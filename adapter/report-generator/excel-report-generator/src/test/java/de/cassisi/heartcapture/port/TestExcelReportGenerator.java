package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestExcelReportGenerator {

    @Test
    void test() {
        ExcelReportFileGeneratorImpl generator = new ExcelReportFileGeneratorImpl();

        Operation operation = new Operation(1L, LocalDate.now(), "Room 7",
                false,
                false,
                false,
                false,
                false);

        PatientData patientData = new PatientData(
                LocalDate.of(1992, 12, 19),
                PatientData.Sex.MALE,
                65,
                "A",
                "Pos",
                187,
                88,
                1.8,
                2.0,
                1.2);

        List<HlmBloodSample> bloodSamples = new ArrayList<>();
        bloodSamples.add(HlmBloodSample.builder().timestamp(LocalDateTime.now()).typ(HlmBloodSample.Type.ART).build());
        bloodSamples.add(HlmBloodSample.builder().timestamp(LocalDateTime.now().plusSeconds(30)).typ(HlmBloodSample.Type.VEN).build());

        List<HlmParamData> paramData = new ArrayList<>();
        paramData.add(HlmParamData.builder().timestamp(LocalDateTime.now().plusSeconds(2)).artFlow(2.0).pressure2(2.3).build());

        HLMData hlmData = new HLMData(
                new ArrayList<>(),
                bloodSamples, paramData, null, null, null, patientData, new MachineData("oxy", "haemo", "kanuel-art", "kanuel-ven", "kanuel-ven-2"), new PrimingComposition(0, Arrays.asList(new Priming("a", 10, "ml"), new Priming("b", 20, "ml"))));

        List<AnesthesiaData> anesthesiaData = Arrays.asList(new AnesthesiaData(LocalDateTime.now(), 34), new AnesthesiaData(LocalDateTime.now().minusSeconds(-5), 55));
        List<NIRSData> nirsData = Arrays.asList(generateNIRSData(LocalDateTime.now(), 32, 33), generateNIRSData(LocalDateTime.now().plusSeconds(6), 44, 56));
        List<InfusionData> infusionDataList = new ArrayList<>();

        PreMedicationData preMedicationData = new PreMedicationData(0,0,0,0,0,0,0,0,0,0,0,0,0);

        ReportData reportData = new ReportData(operation, hlmData, nirsData, infusionDataList, anesthesiaData, preMedicationData);
        generator.generateReport(reportData);
    }


    private NIRSData generateNIRSData(LocalDateTime timestamp, int left, int right) {
        return new NIRSData(timestamp, left, right);
    }

}
