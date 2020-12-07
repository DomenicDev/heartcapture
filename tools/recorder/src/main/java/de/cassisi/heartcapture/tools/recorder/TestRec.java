package de.cassisi.heartcapture.tools.recorder;

import de.cassisi.heartcapture.tools.recorder.bis.BisRS232Recorder;
import de.cassisi.heartcapture.tools.recorder.exception.RecorderException;

public class TestRec {

    public static void main(String[] args) throws RecorderException {
        BisRS232Recorder bis = new BisRS232Recorder("COM7");
        bis.setListener(data -> System.out.println(data.depthOfAnesthesia));
        bis.start();
    }
}
