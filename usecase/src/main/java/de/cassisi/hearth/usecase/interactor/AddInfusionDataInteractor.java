package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.AddInfusionDataRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static de.cassisi.hearth.usecase.AddInfusionData.InputData.*;

public class AddInfusionDataInteractor implements AddInfusionData {

    private AddInfusionDataRepository repository;

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract input data
        long operationId = input.operationId;
        LocalDateTime timestamp = input.timestamp;
        List<PerfusorInput> perfusorInputList = input.infusionData;

        // do some validation
        // todo

        // create infusion data entity
        List<InfusionData.PerfusorData> perfusorData = new ArrayList<>();
        perfusorInputList.forEach(data -> perfusorData.add(new InfusionData.PerfusorData(data.name, data.rate)));
        InfusionData infusionData = new InfusionData(timestamp, perfusorData);

        // save to repository
        repository.addInfusionDataToOperation(operationId, infusionData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputHandler.handle(outputData);

    }
}
