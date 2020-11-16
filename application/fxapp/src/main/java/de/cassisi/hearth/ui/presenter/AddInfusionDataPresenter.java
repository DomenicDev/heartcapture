package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.ui.view.recording.RecordingViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.cassisi.hearth.usecase.AddInfusionData.OutputData;

@Component
public class AddInfusionDataPresenter extends UseCasePresenter<OutputData> {

    private final RecordingViewModel viewModel;

    public AddInfusionDataPresenter(RecordingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(OutputData outputData) {
        List<OutputData.PerfusorData> perfusorData = outputData.data;
        viewModel.getInfusionData().setValue(PresenterUtils.convertInfusionData(perfusorData));
    }

}
