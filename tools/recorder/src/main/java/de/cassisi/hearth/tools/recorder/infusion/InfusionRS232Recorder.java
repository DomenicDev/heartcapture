package de.cassisi.hearth.tools.recorder.infusion;

import de.cassisi.hearth.tools.recorder.AbstractRS232Recorder;
import de.cassisi.hearth.tools.recorder.exception.ConversionException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InfusionRS232Recorder extends AbstractRS232Recorder<InfusionData> {
    public InfusionRS232Recorder(String port) {
        super(port, new byte[] {'\n'}, true);
    }

    @Override
    protected InfusionData convert(String data) throws ConversionException {
        // TODO
        List<InfusionData.Perfusion> perfusions = new ArrayList<>();
        perfusions.add(new InfusionData.Perfusion("Med A", (int) (Math.random()*10)));
        perfusions.add(new InfusionData.Perfusion("Med B", (int) (Math.random()*5)));
        return new InfusionData(LocalDateTime.now(), perfusions);
    }
}
