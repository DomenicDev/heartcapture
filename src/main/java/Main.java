import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello Freiburg!");


        for (SerialPort commPort : SerialPort.getCommPorts()) {
            System.out.println(commPort.getSystemPortName());
        }

        SerialPort port = SerialPort.getCommPort("ttyUSB0");
        port.openPort();
        port.addDataListener(new SerialPortMessageListener() {
            @Override
            public byte[] getMessageDelimiter() {
                return new byte[] {(byte)'\n'};
            }

            @Override
            public boolean delimiterIndicatesEndOfMessage() {
                return true;
            }

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] data = event.getReceivedData();
                String asciiData = new String(data);
                System.out.print(asciiData);

            }
        });


      //  port.closePort();
    }

}
