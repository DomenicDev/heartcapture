package de.cassisi.hearth.tools.recorder.bis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBisRS232Recorder {

    @Test
    void testConversion() {
        BisRS232Recorder recorder = new BisRS232Recorder("NULL");
        String testData = " 11/19/2020 14:22:07|      10|      27|On      |Low     |      40|      60|Yes     |     5.9|    14.9|    1010|    28.8|    63.4|    26.4|    90.4|      10|00000080|     0.0|     0.0|    8000|     0.0|     0.0|     0.0|   100.0|       5|00000080|     5.9|    14.9|    1010|    28.8|    63.4|    26.4|    90.4|       0|00000080|";

        BISData result = recorder.convert(testData);
        double bsi = result.depthOfAnesthesia;

        assertEquals(28.8, bsi);
    }

}
