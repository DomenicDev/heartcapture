package de.cassisi.hearth.ui.operation.overview;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OperationViewViewModel implements ViewModel {

    private final ObjectProperty<TreeItem<OperationTableData>> operationData = new SimpleObjectProperty<>();

}
