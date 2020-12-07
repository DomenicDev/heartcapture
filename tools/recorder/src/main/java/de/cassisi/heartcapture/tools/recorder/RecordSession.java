package de.cassisi.heartcapture.tools.recorder;


import de.cassisi.heartcapture.tools.recorder.exception.RecorderException;

public class RecordSession {

    private final Profile profile;
    private boolean running = false;

    private RecordSession(Profile profile) {
        this.profile = profile;
    }

    /**
     * Creates a new Session based on the specified profile.
     *
     * @param profile the profile containing all recorders, must not be null
     * @return new instance of RecordSession
     */
    public static RecordSession create(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("specified profile must not be null");
        }
        return new RecordSession(profile);
    }

    /**
     * Starts the recording session.
     */
    public void startRecording() throws RecorderException {
        if (running) {
            throw new IllegalStateException("Session already running!");
        }

        try {
            for (Recorder<?> recorder : profile.getRecorderList()) {
                recorder.start();
            }
            this.running = true;
        } catch (RecorderException e) {
            // could not start recording, so we want to stop all recorders
            // that might have started already
            for (Recorder<?> recorder : profile.getRecorderList()) {
                try {
                    recorder.stop();
                } catch (RecorderException ignored) {}
            }
            throw new RecorderException(e.getMessage());
        }

    }

    /**
     * Stops the recording session
     */
    public void stopRecording() throws RecorderException {
        if (!running) {
            throw new IllegalStateException("Session already stopped");
        }

        try {
            for (Recorder<?> recorder : profile.getRecorderList()) {
                recorder.stop();
            }
            this.running = false;
        } catch (RecorderException e) {
            throw new RecorderException(e.getMessage());
        }

    }

    /**
     * @return true if recording is active, false otherwise
     */
    public boolean isRunning() {
        return running;
    }
}
