package de.cassisi.hearth;

import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.CreateOperation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseTests implements CommandLineRunner {

    private CreateOperation createOperation;
    private AddInfusionData addInfusionData;
    private AddNirsData addNirsData;

    public DatabaseTests(CreateOperation createOperation, AddInfusionData addInfusionData, AddNirsData addNirsData) {
        this.createOperation = createOperation;
        this.addInfusionData = addInfusionData;
        this.addNirsData = addNirsData;
    }

    @Override
    public void run(String... args) throws Exception {

        // Create Operation first
        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.localDate = LocalDate.now();
        inputData.room = "Room 4";

        createOperation.execute(inputData, outputData -> System.out.println(outputData.result));

        // operation with id: 1 created...

        // lets add infusion data to it
        AddInfusionData.InputData addInfusionDataInputData = new AddInfusionData.InputData();
        addInfusionDataInputData.timestamp = LocalDateTime.now();
        addInfusionDataInputData.operationId = 1;
        addInfusionDataInputData.infusionData = List.of(new AddInfusionData.InputData.PerfusorInput("medi", 3));
        addInfusionData.execute(addInfusionDataInputData, System.out::println);


        // add nirs data
        AddNirsData.InputData nirsInputData = new AddNirsData.InputData();
        nirsInputData.rightSaturation = 10;
        nirsInputData.leftSaturation = 23;
        nirsInputData.timestamp = LocalDateTime.now();
        nirsInputData.operationId = 1;
        addNirsData.execute(nirsInputData, outputData -> System.out.println(outputData.saved));


    }
}
