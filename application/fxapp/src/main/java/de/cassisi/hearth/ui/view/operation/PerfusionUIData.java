package de.cassisi.hearth.ui.view.operation;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public final class PerfusionUIData extends RecursiveTreeObject<PerfusionUIData> {

    private final StringProperty name;
    private final DoubleProperty rate;

    public PerfusionUIData(String name, double rate) {
        this.name = new SimpleStringProperty(name);
        this.rate = new SimpleDoubleProperty(rate);
    }

}
