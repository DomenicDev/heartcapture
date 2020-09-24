package de.cassisi.hearth;

import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;

import static de.cassisi.hearth.usecase.CreateOperation.*;

public class DefaultPresenter implements OutputHandler<OutputData> {

    private ViewModel viewModel;

    public DefaultPresenter(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(OutputData outputData) {
        String result = outputData.result;
        viewModel.labelText = result;
    }


}
