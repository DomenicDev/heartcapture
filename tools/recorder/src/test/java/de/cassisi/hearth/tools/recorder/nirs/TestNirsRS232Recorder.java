package de.cassisi.hearth.tools.recorder.nirs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNirsRS232Recorder {

    @Test
    void testSuccessfulNirsDataConversion() {
        NirsRS232Recorder recorder = new NirsRS232Recorder("NULL");
        String testData = "7.2.6.0/1/1  10/05/20  10:05:22  L  80  0  1  0  0  0  40  0.0000    0.0000    0  R  45  0  2  0  0  0  78  0.0000    0.0000    0  S1  0  0  11  0  0  0  40  0.0000    0.0000    0  S2  0  0  11  0  0  0  40  0.0000    0.0000    0  0  A12004020269-0  0  0  ";

        NIRSData result = recorder.convert(testData);
        int left = result.left;
        int right = result.right;

        assertEquals(80, left);
        assertEquals(45, right);
    }

}
