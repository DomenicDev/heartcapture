package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.AddAnesthesiaData;
import de.cassisi.heartcapture.usecase.output.AddAnesthesiaDataOutputHandler;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.AddAnesthesiaDataRepository;
import de.cassisi.heartcapture.entity.AnesthesiaData;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TestAddAnesthesiaDataInteractor {

    private final AddAnesthesiaDataRepository repository = mock(AddAnesthesiaDataRepository.class);
    private final OutputHandler<AddAnesthesiaData.OutputData> outputHandler = mock(AddAnesthesiaDataOutputHandler.class);

    private AddAnesthesiaDataInteractor interactor;

    @BeforeEach
    void init() {
        this.interactor = new AddAnesthesiaDataInteractor(repository);
    }

    @Test
    void testSuccessfulUseCase() {
        AddAnesthesiaData.InputData inputData = getValidTestInputData();
        interactor.execute(inputData, outputHandler);

        verify(repository).addAnesthesiaData(anyLong(), any(AnesthesiaData.class));
        verify(outputHandler).handle(any(AddAnesthesiaData.OutputData.class));
    }

    @Test
    void testInvalidOperationId() {
        AddAnesthesiaData.InputData inputData = getValidTestInputData();
        inputData.operationId = -1;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidDate() {
        AddAnesthesiaData.InputData inputData = getValidTestInputData();
        inputData.timestamp = null;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidDepthOfAnesthesia() {
        AddAnesthesiaData.InputData inputData = getValidTestInputData();

        // value below 0 should not be allowed
        inputData.depthOfAnesthesia = -1;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));

        // value above 100 should not be allowed
        inputData.depthOfAnesthesia = 101;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));
    }

    private AddAnesthesiaData.InputData getValidTestInputData() {
        AddAnesthesiaData.InputData inputData = new AddAnesthesiaData.InputData();
        inputData.depthOfAnesthesia = 35;
        inputData.timestamp = LocalDateTime.now();
        inputData.operationId = 10;
        return inputData;
    }

}
