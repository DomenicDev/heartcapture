package de.cassisi.hearth.ui.view.operation.overview;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import lombok.Getter;

@Getter
public class OperationTableData extends RecursiveTreeObject<OperationTableData> {

    private final LongProperty id;
    private final StringProperty date;
    private final StringProperty room;
    private final BooleanProperty liveDataAvailable;
    private final BooleanProperty hlmDataAvailable;

    public OperationTableData(long id, String date, String room, boolean liveDataAvailable, boolean hlmDataAvailable) {
        this.id = new SimpleLongProperty(id);
        this.date = new SimpleStringProperty(date);
        this.room = new SimpleStringProperty(room);
        this.liveDataAvailable = new SimpleBooleanProperty(liveDataAvailable);
        this.hlmDataAvailable = new SimpleBooleanProperty(hlmDataAvailable);
    }

}
