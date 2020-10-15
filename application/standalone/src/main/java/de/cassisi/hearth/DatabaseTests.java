package de.cassisi.hearth;

import de.cassisi.hearth.usecase.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class DatabaseTests implements CommandLineRunner {

    private CreateOperation createOperation;
    private AddInfusionData addInfusionData;
    private AddNirsData addNirsData;
    private ReadHLMDataFile readHLMDataFile;

    public DatabaseTests(CreateOperation createOperation, AddInfusionData addInfusionData, AddNirsData addNirsData, ReadHLMDataFile readHLMDataFile) {
        this.createOperation = createOperation;
        this.addInfusionData = addInfusionData;
        this.addNirsData = addNirsData;
        this.readHLMDataFile = readHLMDataFile;
    }

    @Override
    public void run(String... args) {

        // Create Operation first
        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.localDate = LocalDate.now();
        inputData.room = "Room 4";

       // createOperation.execute(inputData, outputData -> System.out.println(outputData.result));

        // operation with id: 1 created...

        ReadHLMDataFile.InputData readFileInputData = new ReadHLMDataFile.InputData();
        try {
            readFileInputData.hlmFile = new File(Objects.requireNonNull(DatabaseTests.class.getClassLoader().getResource("hlm_test_data_1.xlsx")).toURI());
            readFileInputData.operationId = 1;
            readHLMDataFile.execute(readFileInputData, outputData -> {

            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }




        // lets add infusion data to it


        new Thread(() -> {
            while (true) {
                AddInfusionData.InputData addInfusionDataInputData = new AddInfusionData.InputData();
                addInfusionDataInputData.timestamp = LocalDateTime.now();
                addInfusionDataInputData.operationId = 1;
                addInfusionDataInputData.infusionData = List.of(new AddInfusionData.InputData.PerfusorInput("medi", 3));
                addInfusionData.execute(addInfusionDataInputData, System.out::println);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        // add nirs data
        new Thread(() -> {
            while (true) {
                AddNirsData.InputData nirsInputData = new AddNirsData.InputData();
                nirsInputData.rightSaturation = (int) (Math.random() * 100);
                nirsInputData.leftSaturation = (int) (Math.random() * 100);
                nirsInputData.timestamp = LocalDateTime.now();
                nirsInputData.operationId = 1;
                addNirsData.execute(nirsInputData, outputData -> System.out.println(outputData.saved));

                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }
}
