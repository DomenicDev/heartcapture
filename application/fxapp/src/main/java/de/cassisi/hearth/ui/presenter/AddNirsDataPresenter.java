package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.dashboard.DashboardViewModel;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class AddNirsDataPresenter implements OutputHandler<AddNirsData.OutputData> {

    private DashboardViewModel dashboardViewModel;

    public AddNirsDataPresenter(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void handle(AddNirsData.OutputData outputData) {
        boolean saved = outputData.saved;
        dashboardViewModel.testMessage.setValue("new state: " + saved);
    }
}
