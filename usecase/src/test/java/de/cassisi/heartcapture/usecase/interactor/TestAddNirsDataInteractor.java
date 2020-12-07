package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.AddNirsData;
import de.cassisi.heartcapture.usecase.output.AddNirsDataOutputHandler;
import de.cassisi.heartcapture.usecase.port.AddNirsDataRepository;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
        AddNirsData.InputData inputData = getValidTestInputData();
        interactor.execute(inputData, outputHandler);

        verify(repository).addNirsDataToOperation(eq(inputData.operationId), any());
        verify(outputHandler).handle(any());
    }

    @Test
    void invalidIdShouldFail() {
        AddNirsData.InputData inputData = getValidTestInputData();
        inputData.operationId = -1;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void invalidTimestampShouldFail() {
        AddNirsData.InputData inputData = getValidTestInputData();
        inputData.timestamp = null;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void invalidSaturationShouldFail() {
        AddNirsData.InputData inputData = getValidTestInputData();
        inputData.leftSaturation = -1;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    @Test
    void invalidRightSaturationShouldFail() {
        AddNirsData.InputData inputData = getValidTestInputData();
        inputData.rightSaturation = -1;
        assertThrows(InputValidationException.class, () ->
                interactor.execute(inputData, outputHandler));
    }

    private AddNirsData.InputData getValidTestInputData() {
        AddNirsData.InputData inputData = new AddNirsData.InputData();
        inputData.operationId = 1;
        inputData.leftSaturation = 50;
        inputData.rightSaturation = 55;
        inputData.timestamp = LocalDateTime.now();
        return inputData;
    }

}
