package de.cassisi.hearth;

import de.cassisi.hearth.usecase.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private CreateOperation createOperation;
    @Autowired
    private FindOperation findOperation;
    @Autowired
    private ReadHLMDataFile readHLMDataFile;
    @Autowired
    private GenerateReport generateReport;
    @Autowired
    private AddAnesthesiaData addAnesthesiaData;
    @Autowired
    private AddInfusionData addInfusionData;
    @Autowired
    private AddNirsData addNirsData;

    private long operationId;

    @Test
    void generateTestReport() throws Exception {
        // CREATE OPERATION
        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.localDate = LocalDate.now();
        inputData.room = "Room 14";
        createOperation.execute(inputData, outputData -> operationId = outputData.operationData.getId());

        // CHECK THAT IT REALLY EXISTS
        FindOperation.InputData findInputData = new FindOperation.InputData();
        findInputData.operationId = operationId;
        findOperation.execute(findInputData, System.out::println);

        // ADD BIS DATA
        Random random = new Random();
        LocalDateTime now = LocalDateTime.of(1999, 1, 1, 13, 37);
        for (int i = 0; i < 100; i++) {
            AddAnesthesiaData.InputData anesthesiaInput = new AddAnesthesiaData.InputData();
            anesthesiaInput.depthOfAnesthesia = random.nextInt(80);
            anesthesiaInput.operationId = operationId;
            anesthesiaInput.timestamp = now.plusSeconds(i * 5);
            addAnesthesiaData.execute(anesthesiaInput, outputData -> {});
        }
        for (int i = 0; i < 150; i++) {
            AddNirsData.InputData nirsInput = new AddNirsData.InputData();
            nirsInput.operationId = operationId;
            nirsInput.rightSaturation = random.nextInt(80) + 20;
            nirsInput.leftSaturation = random.nextInt(80) + 20;
            nirsInput.timestamp = now.plusSeconds(6 * i);
            addNirsData.execute(nirsInput, outputData -> {});
        }
        for (int i = 0; i < 100; i++) {
            AddInfusionData.InputData infusionInput = new AddInfusionData.InputData();
            infusionInput.timestamp = now.plusMinutes(2).plusSeconds(i * 5);
            infusionInput.operationId = operationId;
            infusionInput.infusionData = Arrays.asList(
                    new AddInfusionData.InputData.PerfusorInput("Arterenol", (int) (Math.random() * 10)),
                    new AddInfusionData.InputData.PerfusorInput("Vasopressin", (int) (Math.random() * 10)),
                    new AddInfusionData.InputData.PerfusorInput("Sufentanil", (int) (Math.random() * 10))
            );
            addInfusionData.execute(infusionInput, outputData -> {});
        }


        // READ HLM DATA FILE
        ReadHLMDataFile.InputData readInput = new ReadHLMDataFile.InputData();
        readInput.operationId = operationId;
        readInput.hlmFile = new File(Objects.requireNonNull(IntegrationTest.class.getClassLoader().getResource("hlm_test_data.xlsx")).toURI());
        readHLMDataFile.execute(readInput, System.out::println);

        // GENERATE REPORT
        GenerateReport.InputData generateReportInputData = new GenerateReport.InputData();
        generateReportInputData.operationId = operationId;
        generateReport.execute(generateReportInputData, outputData -> {
            try {
                FileOutputStream fos = new FileOutputStream("build/generated_report_test_export.xlsx");
                fos.write(outputData.reportFile);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
