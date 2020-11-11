package de.cassisi.hearth.ui.navigator;

import de.cassisi.hearth.ui.enums.MessageType;
import de.cassisi.hearth.ui.view.main.MainView;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

@Component
public class Navigator {

    private final MainView mainView;

    private final ResourceBundle bundle = ResourceBundle.getBundle("lang/lang");

    public Navigator(MainView mainView) {
        this.mainView = mainView;
    }

    public void showDashboard() {
        this.mainView.showDashboard();
    }

    public void showOperation() {
        this.mainView.showOperationOverview();
    }

    public void showMessage(String messageKey, MessageType messageType) {
        String message = bundle.getString(messageKey);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("ui.message.header.information"));
        alert.setContentText(message);
        alert.showAndWait();
    }
}
