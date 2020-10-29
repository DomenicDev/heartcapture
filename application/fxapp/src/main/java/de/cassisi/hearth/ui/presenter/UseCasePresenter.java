package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.usecase.output.OutputHandler;

/**
 * This abstract helper class implements the OutputHandler
 * and ensures that any custom code is executed on the UI thread.
 * Therefore implementations must overwrite the abstract <code>runOnUI</code> method.
 * @param <T> the OutputData type, same as OutputHandler<T>
 */
public abstract class UseCasePresenter<T> extends FXPresenter<T> implements OutputHandler<T> {

    @Override
    public final void handle(T outputData) {
        present(outputData);
    }

}
