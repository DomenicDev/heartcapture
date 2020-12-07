package de.cassisi.heartcapture.tools.recorder;

public abstract class AbstractRecorder<T> implements Recorder<T> {

    private Callback<T> callback;

    @Override
    public void setListener(Callback<T> callback) {
        this.callback = callback;
    }

    protected void post(T data) {
        if (callback != null && data != null) {
            callback.callback(data);
        }
    }
}
