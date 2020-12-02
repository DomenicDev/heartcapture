package de.cassisi.hearth.ui.event;

import javafx.stage.Window;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShowHelpWindowEvent {

    private final Window owner;

}
