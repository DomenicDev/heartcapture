package de.cassisi.hearth.ui.utils;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.NodeElement;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import de.cassisi.hearth.ui.lang.LanguageResourceProvider;
import de.cassisi.hearth.ui.view.help.HelpView;
import de.cassisi.hearth.ui.view.help.HelpViewModel;
import de.cassisi.hearth.ui.view.recording.RecordingView;
import de.cassisi.hearth.ui.view.recording.RecordingViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public final class DialogCreator {

    private static final Logger LOGGER = Logger.getLogger(DialogCreator.class);

    private static final ResourceBundle language = LanguageResourceProvider.getLanguageBundle();

    static {
        MvvmFX.setGlobalResourceBundle(language);
    }

    public static String getString(String key) {
        return language.getString(key);
    }

    public static void showCreateOperationDialog(Window owner, CreateOperationSubmitCallback callback) {
        ObjectProperty<LocalDate> localDate = new SimpleObjectProperty<>(LocalDate.now());
        StringProperty roomProperty = new SimpleStringProperty("");
        Button submitButton = new Button(getString("ui.create_operation_dialog.create"));
        submitButton.setDefaultButton(true);

        // create form
        Form form = Form.of(
                Group.of(
                        Field.ofDate(localDate).label(getString("ui.create_operation_dialog.operation_date")).required(true),
                        Field.ofStringType(roomProperty).label(getString("ui.create_operation_dialog.operation_room")).required(true),
                        NodeElement.of(submitButton)
                )
        );

        // create stage
        Stage stage = new Stage();
        stage.setTitle(getString("ui.create_operation_dialog.window_title"));

        // setup callback
        submitButton.setOnAction(e -> {
            form.persist();
            callback.handle(localDate.get(), roomProperty.get());
            stage.close();
        });

        // create and show window
        VBox formPane = new VBox();
        formPane.getChildren().add(new FormRenderer(form));
        stage.setScene(new Scene(formPane));
        stage.setWidth(400);
        stage.setHeight(250);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        stage.show();
    }

    public static void showHelpDialog(Window owner) {
        ViewTuple<HelpView, HelpViewModel> tuple  = FluentViewLoader.fxmlView(HelpView.class).load();

        Stage helpWindow = new Stage();
        Scene helpScene = new Scene(tuple.getView());
        helpWindow.initModality(Modality.APPLICATION_MODAL);
        helpWindow.initOwner(owner);
        helpWindow.setTitle(getString("ui.help_window.title"));
        helpWindow.setScene(helpScene);
        helpWindow.setResizable(false);
        helpWindow.show();
    }

    public interface CreateOperationSubmitCallback {
        void handle(LocalDate operationDate, String room);
    }

    public static void showRecordingDialog(Window owner, long operationId) {
        ViewTuple<RecordingView, RecordingViewModel> tuple = FluentViewLoader.fxmlView(RecordingView.class).load();

        // init operation id
        RecordingViewModel viewModel = tuple.getViewModel();
        viewModel.getIdProperty().set(operationId);

        // setup view
        Parent root = tuple.getView();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle(getString("ui.recording_dialog.window_title"));
        stage.setWidth(1280);
        stage.setHeight(700);
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> tuple.getCodeBehind().handleCloseRequest(event));
        stage.show();
    }

    public static Stage showModalProgressIndicatorWindow(Window owner) {
        try {
            Stage progressWindow = new Stage();
            progressWindow.initStyle(StageStyle.UNDECORATED);
            progressWindow.initOwner(owner);
            progressWindow.initModality(Modality.APPLICATION_MODAL);
            progressWindow.setResizable(false);

            // load view
            Parent root = FXMLLoader.load(Objects.requireNonNull(DialogCreator.class.getClassLoader().getResource("de/cassisi/hearth/ui/view/progress/IndeterminateProgressView.fxml")));
            Scene scene = new Scene(root);
            progressWindow.setScene(scene);
            progressWindow.show();
            return progressWindow;
        } catch (IOException e) {
            LOGGER.error("Could not load indeterminate progress view");
            throw new RuntimeException(e);
        }
    }

}
