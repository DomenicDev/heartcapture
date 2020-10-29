package de.cassisi.hearth.ui.presenter;

import com.fazecast.jSerialComm.SerialPort;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RefreshSerialPortPresenter extends FXPresenter<Void> {

    private final OperationOverviewViewModel viewModel;

    public RefreshSerialPortPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(Void data) {
        ObjectProperty<ObservableList<String>> value = viewModel.getSerialPorts();
        List<String> ports = new ArrayList<>();
        for (SerialPort port : SerialPort.getCommPorts()) {
            String systemPortName = port.getSystemPortName();
            ports.add(systemPortName);
        }
        value.setValue(FXCollections.observableArrayList(ports));
    }
}
