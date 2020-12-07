package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import de.cassisi.heartcapture.ui.view.recording.RecordingViewModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.cassisi.heartcapture.usecase.AddInfusionData.OutputData;

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
