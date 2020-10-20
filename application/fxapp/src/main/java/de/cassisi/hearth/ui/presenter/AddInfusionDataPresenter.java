package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.data.PerfusionUIData;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.output.OutputHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static de.cassisi.hearth.usecase.AddInfusionData.OutputData;

@Component
public class AddInfusionDataPresenter implements OutputHandler<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public AddInfusionDataPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(OutputData outputData) {
        List<OutputData.PerfusorData> perfusorData = outputData.data;

        List<PerfusionUIData> uiData = perfusorData.stream()
                .map(data -> new PerfusionUIData(data.name, data.rate))
                .collect(Collectors.toList());


        Platform.runLater(() -> viewModel.infusionList().set((FXCollections.observableArrayList(uiData))));

    }
}
