package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.lang.LanguageReceiver;
import javafx.application.Platform;

/**
 * An abstract helper class to ensure all presenters code is called on the UI thread.
 * @param <T> the type of output data (can also be Void if none is processed)
 */
public abstract class FXPresenter<T> implements LanguageReceiver {

    /**
     * This method calls the abstract method <code>runOnUI</code> on the UI thread.
     * @param data the data to process
     */
    public void present(T data) {
        Platform.runLater(() -> runOnUI(data));
    }

    /**
     * The code implemented in this method is run in the UI Thread.
     * @param data the output data to be handled by the presenter
     */
    protected abstract void runOnUI(T data);

}
