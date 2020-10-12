package de.cassisi.hearth.ui.interactor;

import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultExecutor implements UseCaseExecutor {

    private final AddNirsData addNirsData;
    private final CreateOperation createOperation;

    public DefaultExecutor(AddNirsData addNirsData, CreateOperation createOperation) {
        this.addNirsData = addNirsData;
        this.createOperation = createOperation;
    }

    @Override
    public void addNirsData(AddNirsData.InputData inputData, OutputHandler<AddNirsData.OutputData> outputHandler) {
        addNirsData.execute(inputData, outputHandler);
    }

    @Override
    public void createOperation(CreateOperation.InputData inputData, OutputHandler<CreateOperation.OutputData> outputHandler) {
        createOperation.execute(inputData, outputHandler);
    }
}
