package de.cassisi.hearth.ui.dashboard;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.cassisi.hearth.ui.data.LatestOperation;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class DashboardViewModel implements ViewModel {

    private final ObjectProperty<TreeItem<LatestOperation>> latestOperation = new SimpleObjectProperty<>();

}
