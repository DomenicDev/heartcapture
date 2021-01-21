package de.cassisi.heartcapture.tools.recorder.nirs;

import de.cassisi.heartcapture.tools.recorder.AbstractRS232Recorder;
import de.cassisi.heartcapture.tools.recorder.exception.ConversionException;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NirsRS232Recorder extends AbstractRS232Recorder<NIRSData> {

    private final String regex = "^.*L\\s+(\\d*).*R\\s+(\\d+).*$";
    private final Pattern pattern = Pattern.compile(regex);

    public NirsRS232Recorder(String port) {
        super(port, new byte[] {'\r', '\n'}, true);
    }

    @Override
    protected NIRSData convert(String data) throws ConversionException {
        data = data.trim();
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            int left = Integer.parseInt(matcher.group(1));
            int right = Integer.parseInt(matcher.group(2));
            return new NIRSData(LocalDateTime.now(), left, right);
        } else {
            throw new ConversionException(data, NIRSData.class);
        }
    }

}
