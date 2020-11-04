package de.cassisi.hearth.tools.recorder.detect;

import com.fazecast.jSerialComm.SerialPort;
import de.cassisi.hearth.tools.recorder.Recorder;
import de.cassisi.hearth.tools.recorder.bis.BisRS232Recorder;
import de.cassisi.hearth.tools.recorder.exception.RecorderException;
import de.cassisi.hearth.tools.recorder.infusion.InfusionRS232Recorder;
import de.cassisi.hearth.tools.recorder.nirs.NirsRS232Recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * An AutoPortDetector tries to detect on which port a device is running.
 */
public class AutoPortDetector {

    /**
     * Key to access BIS port from result
     */
    public static final String BIS_SERIAL = "BIS_SERIAL";

    /**
     * Key to access NIRS port from result
     */
    public static final String NIRS_SERIAL = "NIRS_SERIAL";

    /**
     * Key to access INFUSION port from result
     */
    public static final String INFUSION_SERIAL = "INFUSION_SERIAL";

    // helper list to keep track of all running recorders
    // this is helpful to easily close all recorders properly
    private final List<Recorder<?>> activeRecorders = new ArrayList<>();

    public static void main(String[] args) {
        AutoPortDetector detector = new AutoPortDetector();
        DetectionResult result = detector.detectSerialPorts();
        System.out.println("NIRS: " + result.get(NIRS_SERIAL));
        System.out.println("INFUSION: " + result.get(INFUSION_SERIAL));
        System.out.println("BIS:  " + result.get(BIS_SERIAL));
    }

    /**
     * Tries to detect the port of the specified device.
     * If it can not be detected, one of the exceptions below are thrown.
     *
     * @param recorderSupplier supplier to get recorder instances
     * @param timeout          timeo in seconds until a TimeoutException is thrown
     * @param <T>              the type of recorder
     * @return the port the specified recorder can be used on.
     * @throws InterruptedException if executor thread is interrupted
     * @throws ExecutionException   if an exception occurs during execution
     * @throws TimeoutException     if time limit is exceeded
     */
    private <T extends Recorder<?>> String getPort(RS232Supplier<T> recorderSupplier, int timeout) throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        ExecutorService executor = Executors.newCachedThreadPool();

        for (SerialPort serialPort : SerialPort.getCommPorts()) {
            executor.execute(() -> {
                String port = serialPort.getSystemPortName();
                Recorder<?> recorder = recorderSupplier.get(port);
                recorder.setListener(data -> future.complete(port));
                startRecorder(recorder);
            });
        }
        executor.shutdown();
        return future.get(timeout, TimeUnit.SECONDS);
    }

    /**
     * Detects the serial ports of the following devices:
     * <ul>
     *     <li>BIS: {@link BisRS232Recorder}</li>
     *     <li>NIRS: {@link NirsRS232Recorder}</li>
     *     <li>Infusion: {@link InfusionRS232Recorder}</li>
     * </ul>
     * and returns them in a {@link DetectionResult}.
     *
     * @return the DetectionResult object containing the devices port names that also can be null
     */
    public DetectionResult detectSerialPorts() {
        DetectionResult result = new DetectionResult();

        searchPort(result, BIS_SERIAL, BisRS232Recorder::new, 10);
        searchPort(result, NIRS_SERIAL, NirsRS232Recorder::new, 10);
        searchPort(result, INFUSION_SERIAL, InfusionRS232Recorder::new, 15);

        return result;
    }


    /**
     * Helper method for easier reuse / access of the getPort() method.
     * This method tries to detect the port and writes the result into the detection result object.
     *
     * @param result           the result to
     * @param device           the device key
     * @param recorderSupplier the supplier to get instances of the specific serial recorder
     * @param secondsTimeout   the timeout in seconds
     * @param <T>              the recorder type
     */
    private <T extends Recorder<?>> void searchPort(DetectionResult result, String device, RS232Supplier<T> recorderSupplier, int secondsTimeout) {
        try {
            String port = getPort(recorderSupplier, secondsTimeout);
            result.put(device, port);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            stopAndClearActiveRecorders();
        }
    }


    private interface RS232Supplier<T extends Recorder<?>> {

        /**
         * Returns an instance of an serial recorder that uses the specified port.
         *
         * @param port the port to use by the returned recorder
         * @return an instance of the specific recorder
         */
        T get(String port);

    }

    /**
     * Stops all recorders and removes them from the activeRecorders list.
     */
    private void stopAndClearActiveRecorders() {
        this.activeRecorders.forEach(recorder -> {
            try {
                recorder.stop();
            } catch (RecorderException e) {
                e.printStackTrace();
            }
        });
        this.activeRecorders.clear();
    }

    /**
     * Starts the specified recorder and adds the recorder to the activeRecorder list.
     *
     * @param recorder
     */
    public void startRecorder(Recorder<?> recorder) {
        try {
            recorder.start();
            addAsActiveRecorder(recorder);
        } catch (RecorderException e) {
            e.printStackTrace();
        }
    }

    private void addAsActiveRecorder(Recorder<?> recorder) {
        this.activeRecorders.add(recorder);
    }

}
