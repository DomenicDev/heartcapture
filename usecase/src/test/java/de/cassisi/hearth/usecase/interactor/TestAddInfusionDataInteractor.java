package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.usecase.exception.IdMustBePositiveException;
import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.exception.InvalidPerfusorInputException;
import de.cassisi.hearth.usecase.exception.InvalidTimestampException;
import de.cassisi.hearth.usecase.output.AddInfusionDataOutputHandler;
import de.cassisi.hearth.usecase.port.AddInfusionDataRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static de.cassisi.hearth.usecase.AddInfusionData.*;
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
        InputData inputData = getValidTestInputData();
        interactor.execute(inputData, outputHandler);

        verify(repository).addInfusionDataToOperation(anyLong(), any());
        verify(outputHandler).handle(any());
    }

    @Test
    void testInvalidOperationId() {
        InputData inputData = getValidTestInputData();
        inputData.operationId = -1;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidTimestamp() {
        InputData inputData = getValidTestInputData();
        inputData.timestamp = null;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidPerfusorData() {
        InputData inputData = getValidTestInputData();
        inputData.infusionData = null;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    private InputData getValidTestInputData() {
        InputData inputData = new InputData();
        inputData.operationId = 1;
        inputData.timestamp = LocalDateTime.now();

        InputData.PerfusorInput pi1 = new InputData.PerfusorInput();
        pi1.name = "a";
        pi1.rate = 2;
        inputData.infusionData = Arrays.asList(pi1, pi1);
        return inputData;
    }
}
