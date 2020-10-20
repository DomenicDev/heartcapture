package de.cassisi.hearth.ui.recorder;

import de.cassisi.hearth.ui.recorder.data.NIRSData;

import java.time.LocalDateTime;

public class NIRSSimulation extends AbstractRecorder<NIRSData> implements Runnable {

    private final Thread thread = new Thread(this);
    private boolean active;

    @Override
    public void start() {
        this.active = true;
        this.thread.setDaemon(true);
        this.thread.start();
    }

    @Override
    public void stop() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {

            int left = (int) (Math.random() * 30);
            int right = (int) (Math.random() * 30);
            NIRSData nirsData = new NIRSData(LocalDateTime.now(), left, right);
            post(nirsData);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
