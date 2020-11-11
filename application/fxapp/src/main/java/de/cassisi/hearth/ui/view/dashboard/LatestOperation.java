package de.cassisi.hearth.ui.view.dashboard;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import lombok.Getter;

@Getter
public class LatestOperation extends RecursiveTreeObject<LatestOperation> {

    private final LongProperty operationId;
    private final StringProperty operationDate;
    private final StringProperty operationRoom;

    public LatestOperation(long operationId, String operationDate, String operationRoom) {
        this.operationId = new SimpleLongProperty(operationId);
        this.operationDate = new SimpleStringProperty(operationDate);
        this.operationRoom = new SimpleStringProperty(operationRoom);
    }

}
