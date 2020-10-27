package de.cassisi.hearth.tools.recorder.infusion;

import de.cassisi.hearth.tools.recorder.AbstractRecorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InfusionSimulation extends AbstractRecorder<InfusionData> implements Runnable {

    private final Thread thread = new Thread(this);
    private boolean active = false;

    @Override
    public void start() {
        this.thread.setDaemon(true);
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
            List<InfusionData.Perfusion> data = new ArrayList<>();
            data.add(new InfusionData.Perfusion("med_a", (int) (Math.random()*10)));
            data.add(new InfusionData.Perfusion("med_b", (int) (Math.random()*10)));
            InfusionData infusionData = new InfusionData(LocalDateTime.now(), data);
            post(infusionData);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
