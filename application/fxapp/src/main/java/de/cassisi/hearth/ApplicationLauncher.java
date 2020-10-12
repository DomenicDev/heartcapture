package de.cassisi.hearth;

import de.cassisi.hearth.ui.dashboard.DashboardView;
import de.cassisi.hearth.ui.dashboard.MyViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.spring.MvvmfxSpringApplication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationLauncher extends MvvmfxSpringApplication {


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void startMvvmfx(Stage stage) {
        stage.setTitle("TestApp");
        stage.setFullScreen(false);
        stage.setWidth(600);
        stage.setHeight(400);

        ViewTuple<DashboardView, MyViewModel> tuple = FluentViewLoader.fxmlView(DashboardView.class).load();
        stage.setScene(new Scene(tuple.getView()));
        stage.show();
    }


}
