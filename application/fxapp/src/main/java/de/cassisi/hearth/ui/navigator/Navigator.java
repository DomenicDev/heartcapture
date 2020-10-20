package de.cassisi.hearth.ui.navigator;

import de.cassisi.hearth.ui.main.MainView;
import org.springframework.stereotype.Component;

@Component
public class Navigator {

    private final MainView mainView;

    public Navigator(MainView mainView) {
        this.mainView = mainView;
    }

    public void showDashboard() {
        this.mainView.showDashboard();
    }

    public void showOperation() {
        this.mainView.showOperationOverview();
    }

}
