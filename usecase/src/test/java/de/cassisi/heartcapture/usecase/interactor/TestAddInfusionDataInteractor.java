package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.AddInfusionData;
import de.cassisi.heartcapture.usecase.output.AddInfusionDataOutputHandler;
import de.cassisi.heartcapture.usecase.port.AddInfusionDataRepository;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TestAddInfusionDataInteractor {

    private AddInfusionDataRepository repository;
    private AddInfusionDataOutputHandler outputHandler;
    private AddInfusionDataInteractor interactor;


    @BeforeEach
    void init() {
        this.repository = mock(AddInfusionDataRepository.class);
        this.outputHandler = mock(AddInfusionDataOutputHandler.class);
        this.interactor = new AddInfusionDataInteractor(repository);
    }

    @Test
    void testSuccessfulUseCase() {
        AddInfusionData.InputData inputData = getValidTestInputData();
        interactor.execute(inputData, outputHandler);

        verify(repository).addInfusionDataToOperation(anyLong(), any());
        verify(outputHandler).handle(any());
    }

    @Test
    void testInvalidOperationId() {
        AddInfusionData.InputData inputData = getValidTestInputData();
        inputData.operationId = -1;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidTimestamp() {
        AddInfusionData.InputData inputData = getValidTestInputData();
        inputData.timestamp = null;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidPerfusorData() {
        AddInfusionData.InputData inputData = getValidTestInputData();
        inputData.infusionData = null;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    private AddInfusionData.InputData getValidTestInputData() {
        AddInfusionData.InputData inputData = new AddInfusionData.InputData();
        inputData.operationId = 1;
        inputData.timestamp = LocalDateTime.now();

        AddInfusionData.InputData.PerfusorInput pi1 = new AddInfusionData.InputData.PerfusorInput();
        pi1.name = "a";
        pi1.rate = 2;
        inputData.infusionData = Arrays.asList(pi1, pi1);
        return inputData;
    }
}
