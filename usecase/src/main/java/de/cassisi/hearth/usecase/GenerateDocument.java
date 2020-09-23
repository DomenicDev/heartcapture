package de.cassisi.hearth.usecase;

import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.entity.ReportDocument;
import de.cassisi.hearth.entity.ids.OperationId;
import de.cassisi.hearth.usecase.port.HLMRepository;
import de.cassisi.hearth.usecase.port.InfusionRepository;
import de.cassisi.hearth.usecase.port.OperationRepository;

import java.util.List;

public class GenerateDocument {

    private OperationRepository operationRepository;
    private InfusionRepository infusionRepository;
    private HLMRepository hlmRepository;


    public void generateDocument(OperationId operationId) {
        Operation operation = operationRepository.findById(operationId);
        // do some validation
        if (!operation.isHlmDataAvailable()) {
            return;
        }

        List<InfusionData> infusionDataList = infusionRepository.getInfusionDataFromOperation(operationId);
        HLMData hlmData = hlmRepository.findByOperation(operationId);

        ReportDocument reportDocument = new ReportDocument();

        // todo...

    }

}
