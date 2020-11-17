package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.ui.view.dashboard.DashboardViewModel;
import de.cassisi.hearth.usecase.dto.SimpleStatisticDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateDashboardStatisticPresenter extends UseCasePresenter<SimpleStatisticDTO> {

    private final DashboardViewModel viewModel;

    public GenerateDashboardStatisticPresenter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }


    @Override
    protected void runOnUI(SimpleStatisticDTO data) {
        viewModel.getNumberOfOperationsProperty().setValue(data.numberOfOperations);
        viewModel.getNumberOfIncompleteOperationsProperty().setValue(data.numberOfIncompleteOperations);
        viewModel.getNumberOfOpenOperationsProperty().setValue(data.numberOfOpenOperations);

        // OPERATION 2 WEEKS DISTRIBUTION (CHART)
        List<LocalDate> sortedDates = new ArrayList<>(data.numberOfOperationsLast2Weeks.keySet());
        sortedDates.sort(LocalDate::compareTo);

        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        sortedDates.forEach(date -> {
            int val = data.numberOfOperationsLast2Weeks.get(date);
            series.getData().add(new XYChart.Data<>(PresenterUtils.formatLocalDate(date), val));
        });
        ObservableList<XYChart.Series<String, Integer>> list = FXCollections.observableArrayList();
        list.add(series);
        viewModel.getOperationChartProperty().setValue(list);


        // PIE CHART - AGE DISTRIBUTION
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        data.ageDistribution.forEach((key, value) -> {
            pieChartData.add(new PieChart.Data(key, value));
        });
        viewModel.getOperationAgeDistributionProperty().setValue(pieChartData);
    }

}
