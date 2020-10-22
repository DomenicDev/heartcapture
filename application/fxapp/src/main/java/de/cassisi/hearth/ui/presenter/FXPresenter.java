package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.usecase.output.OutputHandler;
import javafx.application.Platform;

/**
 * This abstract helper class implements the OutputHandler
 * and ensures that any custom code is executed on the UI thread.
 * Therefore implementations must overwrite the abstract <code>runOnUI</code> method.
 * @param <T> the OutputData type, same as OutputHandler<T>
 */
public abstract class FXPresenter<T> implements OutputHandler<T> {

    /**
     * The code implemented in this method is run in the UI Thread.
     * @param outputData the output data to be handled by the presenter
     */
    public abstract void runOnUI(T outputData);

    @Override
    public final void handle(T outputData) {
        Platform.runLater(() -> runOnUI(outputData));
    }

}
