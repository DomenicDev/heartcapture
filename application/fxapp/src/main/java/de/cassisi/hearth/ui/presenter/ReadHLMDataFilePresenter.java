package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.usecase.ReadHLMDataFile.OutputData;

@Component
public class ReadHLMDataFilePresenter implements OutputHandler<OutputData> {

    private OperationOverviewViewModel viewModel;

    public ReadHLMDataFilePresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(OutputData outputData) {
        SimpleOperationData simpleOperationData = outputData.simpleOperationData;
        // todo...
        System.out.println("presenter");
    }
}
