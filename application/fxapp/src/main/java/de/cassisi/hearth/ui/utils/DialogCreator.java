package de.cassisi.hearth.ui.utils;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.NodeElement;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.time.LocalDate;

public final class DialogCreator {

    public static void showCreateOperationDialog(Window owner, CreateOperationSubmitCallback callback) {
        ObjectProperty<LocalDate> localDate = new SimpleObjectProperty<>(LocalDate.now());
        StringProperty roomProperty = new SimpleStringProperty("");
        Button submitButton = new Button("Anlegen");

        // create form
        Form form = Form.of(
                Group.of(
                        Field.ofDate(localDate).label("Datum").required(true),
                        Field.ofStringType(roomProperty).label("Raum").required(true),
                        NodeElement.of(submitButton)
                )
        ).title("Der Titel");

        // create stage
        Stage stage = new Stage();
        stage.setTitle("Neue Operation anlegen");

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

    public interface CreateOperationSubmitCallback {
        void handle(LocalDate operationDate, String room);
    }

}
