package de.cassisi.heartcapture.ui.view.premedication;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PreMedicationViewModel implements ViewModel {

    private final LongProperty operationIdProperty = new SimpleLongProperty();

    // pre-operation
    private final DoubleProperty suprareninPreOperationProperty = new SimpleDoubleProperty();
    private final DoubleProperty norepinephrinPreOperationProperty = new SimpleDoubleProperty();
    private final DoubleProperty vasopressinPreOperationProperty = new SimpleDoubleProperty();
    private final DoubleProperty milrinonPreOperationProperty = new SimpleDoubleProperty();
    private final DoubleProperty ntgPreOperationProperty = new SimpleDoubleProperty();
    private final DoubleProperty simdaxPreOperationProperty = new SimpleDoubleProperty();
    private final DoubleProperty heparinPreOperationProperty = new SimpleDoubleProperty();

    // pre-HLM
    private final DoubleProperty suprareninPreHlmProperty = new SimpleDoubleProperty(10D);
    private final DoubleProperty norepinephrinPreHlmProperty = new SimpleDoubleProperty();
    private final DoubleProperty vasopressinPreHlmProperty = new SimpleDoubleProperty();
    private final DoubleProperty milrinonPreHlmProperty = new SimpleDoubleProperty();
    private final DoubleProperty ntgPreHlmProperty = new SimpleDoubleProperty();
    private final DoubleProperty simdaxPreHlmProperty = new SimpleDoubleProperty();

}
