package de.cassisi.hearth.ui.view.dashboard;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public final class DashboardViewModel implements ViewModel {

    private final ObjectProperty<TreeItem<LatestOperation>> latestOperation = new SimpleObjectProperty<>();

}
