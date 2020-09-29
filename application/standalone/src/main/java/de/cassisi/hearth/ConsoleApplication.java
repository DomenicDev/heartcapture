package de.cassisi.hearth;

import de.cassisi.hearth.port.CreateOperationJpaRepository;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    private final CreateOperationJpaRepository repository;
    private final CreateOperation createOperation;

    public ConsoleApplication(CreateOperationJpaRepository repository, CreateOperation createOperation) {
        this.repository = repository;
        this.createOperation = createOperation;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Application started successfully!");

        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.localDate = LocalDate.now();
        inputData.room = "Room 4";



   //     createOperation.execute(inputData, new TestOutputHandler());

    }

    @PostMapping("/operation")
    public CreateOperation.OutputData createOperation(@RequestParam(name = "room") String room) throws ExecutionException, InterruptedException {
        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.room = room;
        inputData.localDate = LocalDate.now();

        CompletableFuture<CreateOperation.OutputData> outputDataCompletableFuture = new CompletableFuture<>();
        createOperation.execute(inputData, outputDataCompletableFuture::complete);
        return outputDataCompletableFuture.get();
    }

    private class TestOutputHandler implements OutputHandler<CreateOperation.OutputData> {

        @Override
        public void handle(CreateOperation.OutputData outputData) {
            System.out.println(outputData.result);
        }
    }
}
