package de.cassisi.hearth.tools.recorder;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;
import de.cassisi.hearth.tools.recorder.exception.ConversionException;
import de.cassisi.hearth.tools.recorder.exception.RecorderException;

import java.util.logging.Logger;

public abstract class AbstractRS232Recorder<T> extends AbstractRecorder<T> implements SerialPortMessageListener {

    private static final Logger LOGGER = Logger.getLogger(AbstractRS232Recorder.class.getName());

    private final String port;
    private final byte[] messageDelimiter;
    private final boolean delimiterIndicatesEndOfMessage;
    private final SerialPort serialPort;

    public AbstractRS232Recorder(String port, byte[] messageDelimiter, boolean delimiterIndicatesEndOfMessage) {
        this.port = port;
        this.messageDelimiter = messageDelimiter;
        this.delimiterIndicatesEndOfMessage = delimiterIndicatesEndOfMessage;

        serialPort = SerialPort.getCommPort(port);
        serialPort.addDataListener(this);
    }

    @Override
    public final void start() throws RecorderException {
        boolean open = serialPort.openPort(1000);
        if (!open) {
            throw new RecorderException("Could not open port " + port);
        }
    }

    @Override
    public final void stop() throws RecorderException {
        boolean closed = serialPort.closePort();
        if (!closed) {
            throw new RecorderException("Could not close port " + port);
        }
    }

    @Override
    public final byte[] getMessageDelimiter() {
        return this.messageDelimiter;
    }

    @Override
    public final boolean delimiterIndicatesEndOfMessage() {
        return this.delimiterIndicatesEndOfMessage;
    }

    @Override
    public final int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public final void serialEvent(SerialPortEvent event) {
        byte[] data = event.getReceivedData();
        String asciiData = new String(data);
        asciiData = asciiData.trim();
        try {
            T result = convert(asciiData);
            if (result != null) {
                post(result);
            }
        } catch (Exception e) {
            LOGGER.fine(e.getMessage());
        }
    }

    /**
     * This method needs to be implemented by the concrete class.
     * Here, the received data is converted to the defined object type.
     * Do not call the method <code>post()</code> in here! This is already called for you after.
     * @param data the received data to be converted
     * @return the specific result object, or null if no data should be posted yet
     * @throws ConversionException if data could not be converted to result object
     */
    protected abstract T convert(String data) throws ConversionException;

}
