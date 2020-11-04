package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import org.springframework.stereotype.Component;

@Component
public class AutoDetectPortStartPresenter extends FXPresenter<Void> {

    private final OperationOverviewViewModel viewModel;

    public AutoDetectPortStartPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(Void data) {
        viewModel.getAutoDetectProgressBarVisible().setValue(true);
    }
}
