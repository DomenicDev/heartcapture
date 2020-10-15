package de.cassisi.hearth;

import de.saxsys.mvvmfx.MvvmFX;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApplicationLauncher extends Application {

    private ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        launch(ApplicationLauncher.class, args);
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(ApplicationLauncher.class).run();
        MvvmFX.setCustomDependencyInjector(applicationContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage source) {
            super(source);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
