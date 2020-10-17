package de.cassisi.hearth;

import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.FindOperation;
import de.cassisi.hearth.usecase.GenerateReport;
import de.cassisi.hearth.usecase.ReadHLMDataFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

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

    private long operationId;

    @Test
    void test() throws Exception {
        // CREATE OPERATION
        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.localDate = LocalDate.now();
        inputData.room = "Room 14";
        createOperation.execute(inputData, outputData -> operationId = outputData.id);

        // CHECK THAT IT REALLY EXISTS
        FindOperation.InputData findInputData = new FindOperation.InputData();
        findInputData.operationId = operationId;
        findOperation.execute(findInputData, System.out::println);

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
