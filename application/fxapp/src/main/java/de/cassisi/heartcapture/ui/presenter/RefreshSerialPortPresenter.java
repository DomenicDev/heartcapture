package de.cassisi.heartcapture.ui.presenter;

import com.fazecast.jSerialComm.SerialPort;
import de.cassisi.heartcapture.ui.preference.UserPreference;
import de.cassisi.heartcapture.ui.view.recording.RecordingViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

@Component
public class RefreshSerialPortPresenter extends FXPresenter<Void> {

    private final Preferences preferences = UserPreference.getInstance().getPreferences();

    private final RecordingViewModel viewModel;

    public RefreshSerialPortPresenter(RecordingViewModel viewModel) {
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

        boolean bisSerialEnabled = getPreferences().getBoolean("BIS_SERIAL_ENABLED", true);
        boolean nirsSerialEnabled = getPreferences().getBoolean("NIRS_SERIAL_ENABLED", true);
        boolean infusionSerialEnabled = getPreferences().getBoolean("INFUSION_SERIAL_ENABLED", true);

        String bisSerialPortName = getPreferences().get("BIS_SERIAL_PORT_NAME", null);
        String nirsSerialPortName = getPreferences().get("NIRS_SERIAL_PORT_NAME", null);
        String infusionSerialPortName = getPreferences().get("INFUSION_SERIAL_PORT_NAME", null);

        viewModel.getBisSerialEnabled().addListener((observable, oldValue, newValue) -> getPreferences().putBoolean("BIS_SERIAL_ENABLED", newValue));
        viewModel.getNirsSerialEnabled().addListener(((observable, oldValue, newValue) -> getPreferences().putBoolean("NIRS_SERIAL_ENABLED", newValue)));
        viewModel.getInfusionSerialEnabled().addListener(((observable, oldValue, newValue) -> getPreferences().putBoolean("INFUSION_SERIAL_ENABLED", newValue)));

        viewModel.getBisSerialPort().addListener((observable, oldValue, newValue) -> put("BIS_SERIAL_PORT_NAME", newValue));
        viewModel.getNirsSerialPort().addListener((observable, oldValue, newValue) -> put("NIRS_SERIAL_PORT_NAME", newValue));
        viewModel.getInfusionSerialPort().addListener((observable, oldValue, newValue) -> put("INFUSION_SERIAL_PORT_NAME", newValue));

        viewModel.getBisSerialEnabled().setValue(bisSerialEnabled);
        viewModel.getNirsSerialEnabled().setValue(nirsSerialEnabled);
        viewModel.getInfusionSerialEnabled().setValue(infusionSerialEnabled);

        viewModel.getBisSerialPort().setValue(bisSerialPortName);
        viewModel.getNirsSerialPort().setValue(nirsSerialPortName);
        viewModel.getInfusionSerialPort().setValue(infusionSerialPortName);
    }

    public Preferences getPreferences() {
        return preferences;
    }

    private void put(String key, String value) {
        if (key != null && value != null) {
            getPreferences().put(key, value);
        }
    }
}
