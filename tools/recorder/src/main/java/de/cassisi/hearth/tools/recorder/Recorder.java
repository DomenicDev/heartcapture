package de.cassisi.hearth.tools.recorder;

import de.cassisi.hearth.tools.recorder.exception.RecorderException;

public interface Recorder<T> {

    void start() throws RecorderException;

    void stop() throws RecorderException;

    void setListener(Callback<T> callback);

}
