package de.cassisi.hearth.ui;

import de.cassisi.hearth.ui.view.main.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.ApplicationLauncher.StageReadyEvent;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Parent root = FluentViewLoader.fxmlView(MainView.class).load().getView();

        Stage stage = event.getStage();
        stage.setTitle("HearthCapture");
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
