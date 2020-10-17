package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.dashboard.DashboardViewModel;
import de.cassisi.hearth.ui.data.LatestOperationTableData;
import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.usecase.FindAllOperations;
import de.cassisi.hearth.usecase.output.OutputHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefreshLatestOperationPresenter implements OutputHandler<FindAllOperations.OutputData> {

    private final DashboardViewModel viewModel;

    public RefreshLatestOperationPresenter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(FindAllOperations.OutputData outputData) {
        List<LatestOperationTableData> tableDataList = PresenterUtils.convert(outputData.simpleOperationDataList);
        ObservableList<LatestOperationTableData> obsData = FXCollections.observableArrayList(tableDataList);
        viewModel.latestOperationData().set(obsData);
    }
}
