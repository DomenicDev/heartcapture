package de.cassisi.hearth.ui.recorder;

import de.cassisi.hearth.ui.recorder.data.BISData;

import java.time.LocalDateTime;

public class BISSimulation extends AbstractRecorder<BISData> implements Runnable {

    private final Thread thread = new Thread(this);
    private boolean active = false;

    public BISSimulation() {
        thread.setDaemon(true);
    }

    @Override
    public void start() {
        active = true;
        thread.start();
    }

    @Override
    public void stop() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {

            BISData data = new BISData(LocalDateTime.now(), (Math.random() * 80));
            post(data);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
