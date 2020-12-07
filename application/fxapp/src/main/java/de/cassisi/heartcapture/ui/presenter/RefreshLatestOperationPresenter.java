package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.dashboard.DashboardViewModel;
import de.cassisi.heartcapture.ui.view.dashboard.LatestOperation;
import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import de.cassisi.heartcapture.usecase.FindAllOperations;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;

@Component
public class RefreshLatestOperationPresenter extends UseCasePresenter<FindAllOperations.OutputData> {

    private final DashboardViewModel viewModel;

    public RefreshLatestOperationPresenter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(FindAllOperations.OutputData outputData) {
        TreeItem<LatestOperation> treeItem = PresenterUtils.convert(outputData.simpleOperationDataList);
        viewModel.getLatestOperation().setValue(treeItem);
    }

}
