package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.FindAllOperations;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.FindAllOperationsRepository;
import de.cassisi.hearth.usecase.util.DTOConverter;

import java.util.List;

public class FindAllOperationsInteractor implements FindAllOperations {

    private final FindAllOperationsRepository repository;

    public FindAllOperationsInteractor(FindAllOperationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        int limit = input.limit;
        boolean sortByLatest = input.sortByLatest;

        List<Operation> operations = repository.findOperations(limit, sortByLatest);

        OutputData outputData = new OutputData();
        operations.forEach(operation -> outputData.simpleOperationDataList.add(DTOConverter.toSimpleOperationData(operation)));
        outputHandler.handle(outputData);
    }
}
