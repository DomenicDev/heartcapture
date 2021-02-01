package de.cassisi.heartcapture.tools.recorder;

public abstract class AbstractRecorder<T> implements Recorder<T> {

    private Callback<T> callback;

    @Override
    public void setListener(Callback<T> callback) {
        this.callback = callback;
    }

    /**
     * Called by subclasses to notify listener about recorded data.
     * @param data the data to notify the listener about.
     */
    protected void post(T data) {
        if (callback != null && data != null) {
            callback.callback(data);
        }
    }
}
