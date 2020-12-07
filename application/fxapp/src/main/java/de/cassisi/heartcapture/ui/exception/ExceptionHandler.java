package de.cassisi.heartcapture.ui.exception;

import de.cassisi.heartcapture.ui.lang.LanguageReceiver;
import de.cassisi.heartcapture.usecase.exception.*;
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

    private void showSimpleWarningDialog(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(message);
            alert.show();
        });

    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public void handle(OperationNotFoundException e) {
        showSimpleWarningDialog(getString("ui.message.operation_not_found.title"), getString("ui.message.operation_not_found.text"));
    }

    public void handle(InputValidationException e) {
        showSimpleWarningDialog(getString("ui.message.input_validation.title"), getString("ui.message.input_validation.text"));
    }

    public void handle(OperationLockException e) {
        showSimpleWarningDialog(getString("ui.message.operation_lock.title"), getString("ui.message.operation_lock.text"));
    }

    public void handle(ReadHLMFileException e) {
        showSimpleWarningDialog(getString("ui.message.read_hlm_file_error.title"), getString("ui.message.read_hlm_file_error.text"));
    }

    public void handle(MissingHlmFileException e) {
        showSimpleWarningDialog(getString("ui.message.missing_hlm_file_error.title"), getString("ui.message.missing_hlm_file_error.text"));
    }

    public void handle(ReportGenerationException e) {
        showSimpleWarningDialog(getString("ui.message.report_generation_error.title"), getString("ui.message.report_generation_error.text"));
    }
}
