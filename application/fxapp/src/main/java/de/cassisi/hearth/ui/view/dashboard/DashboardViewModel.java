package de.cassisi.hearth.ui.view.dashboard;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class DashboardViewModel implements ViewModel {

    private final ObjectProperty<TreeItem<LatestOperation>> latestOperation = new SimpleObjectProperty<>();

    private final IntegerProperty numberOfOperationsProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty numberOfIncompleteOperationsProperty = new SimpleIntegerProperty(0);
    private final IntegerProperty numberOfOpenOperationsProperty = new SimpleIntegerProperty();

    private final ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> operationChartProperty = new SimpleObjectProperty<>(FXCollections.emptyObservableList());
    private final ObjectProperty<ObservableList<PieChart.Data>> operationAgeDistributionProperty = new SimpleObjectProperty<>(FXCollections.emptyObservableList());

}
