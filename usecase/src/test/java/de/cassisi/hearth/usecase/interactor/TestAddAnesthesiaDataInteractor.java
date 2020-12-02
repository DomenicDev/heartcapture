package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.output.AddAnesthesiaDataOutputHandler;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.AddAnesthesiaDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static de.cassisi.hearth.usecase.AddAnesthesiaData.InputData;
import static de.cassisi.hearth.usecase.AddAnesthesiaData.OutputData;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TestAddAnesthesiaDataInteractor {

    private final AddAnesthesiaDataRepository repository = mock(AddAnesthesiaDataRepository.class);
    private final OutputHandler<OutputData> outputHandler = mock(AddAnesthesiaDataOutputHandler.class);

    private AddAnesthesiaDataInteractor interactor;

    @BeforeEach
    void init() {
        this.interactor = new AddAnesthesiaDataInteractor(repository);
    }

    @Test
    void testSuccessfulUseCase() {
        InputData inputData = getValidTestInputData();
        interactor.execute(inputData, outputHandler);

        verify(repository).addAnesthesiaData(anyLong(), any(AnesthesiaData.class));
        verify(outputHandler).handle(any(OutputData.class));
    }

    @Test
    void testInvalidOperationId() {
        InputData inputData = getValidTestInputData();
        inputData.operationId = -1;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidDate() {
        InputData inputData = getValidTestInputData();
        inputData.timestamp = null;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));
    }

    @Test
    void testInvalidDepthOfAnesthesia() {
        InputData inputData = getValidTestInputData();

        // value below 0 should not be allowed
        inputData.depthOfAnesthesia = -1;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));

        // value above 100 should not be allowed
        inputData.depthOfAnesthesia = 101;
        assertThrows(InputValidationException.class, () -> interactor.execute(inputData, outputHandler));
    }

    private InputData getValidTestInputData() {
        InputData inputData = new InputData();
        inputData.depthOfAnesthesia = 35;
        inputData.timestamp = LocalDateTime.now();
        inputData.operationId = 10;
        return inputData;
    }

}
