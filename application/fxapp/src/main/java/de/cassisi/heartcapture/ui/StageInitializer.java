package de.cassisi.heartcapture.ui;

import de.cassisi.heartcapture.ui.view.main.MainView;
import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static de.cassisi.heartcapture.ApplicationLauncher.StageReadyEvent;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Parent root = FluentViewLoader.fxmlView(MainView.class).load().getView();

        Stage stage = event.getStage();
        stage.getIcons().add(new Image(Objects.requireNonNull(StageInitializer.class.getClassLoader().getResourceAsStream("images/icon_64.png"))));
        stage.setTitle("HeartCapture");
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
