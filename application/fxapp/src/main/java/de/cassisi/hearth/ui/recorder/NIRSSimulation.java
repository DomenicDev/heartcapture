package de.cassisi.hearth.ui.recorder;

public class NIRSSimulation extends AbstractRecorder<NIRSSimulation.NIRSData> implements Runnable {

    private final Thread thread = new Thread(this);
    private boolean active;

    @Override
    public void start() {
        this.active = true;
        this.thread.start();
    }

    @Override
    public void stop() {
        this.active = false;
    }

    @Override
    public void run() {
        while (active) {

            NIRSData nirsData = new NIRSData();
            nirsData.left = (int) (Math.random() * 30);
            nirsData.right = (int) (Math.random() * 30);
            post(nirsData);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static class NIRSData {

        public int left;
        public int right;

    }

}
