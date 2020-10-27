package de.cassisi.hearth.tools.recorder.bis;

import de.cassisi.hearth.tools.recorder.AbstractRS232Recorder;
import de.cassisi.hearth.tools.recorder.exception.ConversionException;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BisRS232Recorder extends AbstractRS232Recorder<BISData> {

    private final String regex = "^(?:.*?\\|){13}\\d*\\s(\\d*\\.\\d).*$";
    private final Pattern pattern = Pattern.compile(regex);

    public BisRS232Recorder(String port) {
        super(port, new byte[] {'\n'}, true);
    }

    @Override
    protected BISData convert(String data) throws ConversionException {
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            String depthOfAnesthesia = matcher.group(1);
            double value = Double.parseDouble(depthOfAnesthesia);
            return new BISData(LocalDateTime.now(), value);
        } else {
            throw new ConversionException(data, BISData.class);
        }

    }
}
