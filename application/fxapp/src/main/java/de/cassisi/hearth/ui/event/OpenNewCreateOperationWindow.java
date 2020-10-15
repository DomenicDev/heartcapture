package de.cassisi.hearth.ui.event;

import javafx.stage.Window;

public class OpenNewCreateOperationWindow {

    private final Window owner;

    public OpenNewCreateOperationWindow(Window owner) {
        this.owner = owner;
    }

    public Window getOwner() {
        return owner;
    }
}
