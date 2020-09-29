package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.usecase.exception.IdMustBePositiveException;
import de.cassisi.hearth.usecase.exception.InvalidNirsSaturationException;
import de.cassisi.hearth.usecase.exception.InvalidTimestampException;
import de.cassisi.hearth.usecase.output.AddNirsDataOutputHandler;
import de.cassisi.hearth.usecase.port.AddNirsDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static de.cassisi.hearth.usecase.AddNirsData.InputData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TestAddNirsDataInteractor {

    private AddNirsDataRepository repository;
    private AddNirsDataInteractor interactor;
    private AddNirsDataOutputHandler outputHandler;

    @BeforeEach
    void init() {
        this.outputHandler = mock(AddNirsDataOutputHandler.class);
        this.repository = mock(AddNirsDataRepository.class);
        this.interactor = new AddNirsDataInteractor(repository);
    }

    @Test
    void whenUseCaseExecuted_thenSuccess() {
        InputData inputData = getValidTestInputData();
        interactor.execute(inputData, outputHandler);

        verify(repository).addNirsDataToOperation(eq(inputData.operationId), any());
        verify(outputHandler).handle(any());
    }

    @Test
    void invalidIdShouldFail() {
        InputData inputData = getValidTestInputData();
        inputData.operationId = -1;
        assertThrows(IdMustBePositiveException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void invalidTimestampShouldFail() {
        InputData inputData = getValidTestInputData();
        inputData.timestamp = null;
        assertThrows(InvalidTimestampException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void invalidSaturationShouldFail() {
        InputData inputData = getValidTestInputData();
        inputData.leftSaturation = -1;
        assertThrows(InvalidNirsSaturationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void invalidRightSaturationShouldFail() {
        InputData inputData = getValidTestInputData();
        inputData.rightSaturation = -1;
        assertThrows(InvalidNirsSaturationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    private InputData getValidTestInputData() {
        InputData inputData = new InputData();
        inputData.operationId = 1;
        inputData.leftSaturation = 50;
        inputData.rightSaturation = 55;
        inputData.timestamp = LocalDateTime.now();
        return inputData;
    }

}
