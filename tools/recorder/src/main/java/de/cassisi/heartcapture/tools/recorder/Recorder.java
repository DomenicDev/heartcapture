package de.cassisi.heartcapture.tools.recorder;

import de.cassisi.heartcapture.tools.recorder.exception.RecorderException;

/**
 * Basic interface to be implemented by all recorder classes.
 * @param <T> the type of recorded data
 */
public interface Recorder<T> {

    /**
     * Starts the recording.
     * @throws RecorderException if recording could not be started.
     */
    void start() throws RecorderException;

    /**
     * Stops the recording.
     * @throws RecorderException if recording could not be stopped.
     */
    void stop() throws RecorderException;

    /**
     * Applies the specified callback listener.
     * @param callback the listener to set
     */
    void setListener(Callback<T> callback);

}
