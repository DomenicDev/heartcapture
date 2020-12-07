package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.entity.InfusionData;
import de.cassisi.heartcapture.entity.PerfusorData;
import de.cassisi.heartcapture.usecase.AddInfusionData;
import de.cassisi.heartcapture.usecase.port.AddInfusionDataRepository;
import de.cassisi.heartcapture.usecase.util.OperationUtils;
import de.cassisi.heartcapture.usecase.validator.InputValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.cassisi.heartcapture.usecase.AddInfusionData.InputData.*;

public class AddInfusionDataInteractor implements AddInfusionData {

    private final AddInfusionDataRepository repository;

    public AddInfusionDataInteractor(AddInfusionDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract input data
        long operationId = input.operationId;
        LocalDateTime timestamp = input.timestamp;
        List<PerfusorInput> perfusorInputList = input.infusionData;

        // validate input
        InputValidator.checkIdPositive(operationId);
        InputValidator.checkTimestamp(timestamp);
        InputValidator.checkPerfusorInput(perfusorInputList);

        // check if operation is locked
        OperationUtils.throwIfLocked(operationId, repository.isLocked(operationId));

        // convert input data to perfusor entity objects
        List<PerfusorData> perfusorData = new ArrayList<>();
        perfusorInputList.forEach(data -> perfusorData.add(new PerfusorData(data.name, data.rate)));

        // create infusion entity object
        InfusionData infusionData = new InfusionData(timestamp, perfusorData);

        // save to repository
        repository.addInfusionDataToOperation(operationId, infusionData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.data = perfusorData.stream().map(data -> new AddInfusionData.OutputData.PerfusorData(data.getName(), data.getRate())).collect(Collectors.toList());
        outputHandler.handle(outputData);
    }

}
