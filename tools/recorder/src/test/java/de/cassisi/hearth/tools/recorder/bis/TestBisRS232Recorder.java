package de.cassisi.hearth.tools.recorder.bis;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBisRS232Recorder {

    @Test
    void testConversion() {
        BisRS232Recorder recorder = new BisRS232Recorder("NULL");
        String testData = "09/18/2020 14:01:52|\t10|\t27|On\t|None\t|Off\t|Off\t|No\t|\t0.8|\t14.8|\t0010|\t52.7|\t54.7|\t38.8|\t94.9|\t3|000000080|   0.0|\t0.0|\t8000|\t0.0|\t0.0|\t0.0|\t100.0|\t5|00000080|\t0.8|\t14.8|\t0010|\t52.7|\t54.7|\t38.81|\t94.91\t0|00000080|";

        BISData result = recorder.convert(testData);
        double bsi = result.depthOfAnesthesia;

        assertEquals(38.8, bsi);
    }

}
