package de.cassisi.hearth.ui.exception;

import de.cassisi.hearth.ui.lang.LanguageReceiver;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Component
public class ExceptionHandler implements LanguageReceiver {

    public void handleGenericException(Exception e) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(getString("ui.message.generic_error_title"));
            alert.setHeaderText(getString("ui.message.generic_error_message"));

            VBox dialogPaneContent = new VBox();

            Label label = new Label(getString("ui.message.generic_error_label"));

            String stackTrace = getStackTrace(e);
            TextArea textArea = new TextArea();
            textArea.setText(stackTrace);

            dialogPaneContent.getChildren().addAll(label, textArea);

            // Set content for Dialog Pane
            alert.getDialogPane().setContent(dialogPaneContent);

            alert.showAndWait();
        });

    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String s = sw.toString();
        return s;
    }

}
