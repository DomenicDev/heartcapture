package de.cassisi.heartcapture.tools.recorder.bis;

import de.cassisi.heartcapture.tools.recorder.exception.ConversionException;
import de.cassisi.heartcapture.tools.recorder.AbstractRS232Recorder;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BisRS232Recorder extends AbstractRS232Recorder<BISData> {

    private final String regex = "\\d{2}\\/\\d{2}\\/\\d{4}\\s\\d{2}:\\d{2}:\\d{2}\\|\\s*\\d+\\|\\s*\\d+\\|\\w+\\s*\\|\\w+\\s+\\|\\s+\\d+\\|\\s+\\d+\\|\\w+\\s*\\|\\s+\\d+.\\d+\\|\\s+\\d+.\\d+\\|\\s+\\S{4}\\|\\s+(\\d+.\\d)";
    private final Pattern pattern = Pattern.compile(regex);

    public BisRS232Recorder(String port) {
        super(port, new byte[] {0x0D, 0x0A}, true);
    }

    @Override
    protected BISData convert(String data) throws ConversionException {
        String input = data.trim();
        Matcher matcher = pattern.matcher(input);
        try {
            if (matcher.find()) {
                String depthOfAnesthesia = matcher.group(1);
                double value = Double.parseDouble(depthOfAnesthesia);
                return new BISData(LocalDateTime.now(), value);
            } else {
                throw new ConversionException(data, BISData.class);
            }

        } catch (Exception e) {
           throw new ConversionException(data, BISData.class);
        }

    }
}
