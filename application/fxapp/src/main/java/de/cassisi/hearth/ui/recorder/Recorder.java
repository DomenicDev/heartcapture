package de.cassisi.hearth.ui.recorder;

public interface Recorder<T> {

    void start();

    void stop();

    void setListener(Callback<T> callback);

}
