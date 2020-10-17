package de.cassisi.hearth.ui.recorder;

public abstract class AbstractRecorder<T> implements Recorder<T> {

    private Callback<T> callback;

    @Override
    public void setListener(Callback<T> callback) {
        this.callback = callback;
    }

    protected void post(T data) {
        if (callback != null) {
            callback.callback(data);
        }
    }
}
