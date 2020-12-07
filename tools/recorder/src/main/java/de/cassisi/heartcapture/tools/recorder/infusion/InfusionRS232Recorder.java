package de.cassisi.heartcapture.tools.recorder.infusion;

import de.cassisi.heartcapture.tools.recorder.exception.ConversionException;
import de.cassisi.heartcapture.tools.recorder.AbstractRS232Recorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InfusionRS232Recorder extends AbstractRS232Recorder<InfusionData> {

    private final String NAME_KEY = "INSOL";
    private final String RATE_KEY = "INRT";

    private final Pattern namePattern = getPatternForKey(NAME_KEY);
    private final Pattern ratePattern = getPatternForKey(RATE_KEY);

    private final String regex = "(<etb>|<etx>)(?=\\d+<eot>)";
    private final Pattern endPattern = Pattern.compile(regex);
    private final List<InfusionData.Perfusion> perfs = new ArrayList<>();

    public InfusionRS232Recorder(String port) {
        super(
                port,
                new byte[]{0x04}, // 0x04 --> eot => end of transmission
                true
        );
    }

    private Pattern getPatternForKey(String key) {
        String template = "(?!=<RS>)\\d+,\\d+,+(PARAM,([^<]+))(?=<\\w{2,3}>)";
        String regex = template.replace("PARAM", key);
        return Pattern.compile(regex);
    }

    @Override
    protected InfusionData convert(String data) throws ConversionException {
        try {
            // we need to replace status codes for further processing
            data = data.replaceAll(String.valueOf((char) 0x01), "<soh>");
            data = data.replaceAll(String.valueOf((char) 0x02), "<stx>");
            data = data.replaceAll(String.valueOf((char) 0x03), "<etx>");
            data = data.replaceAll(String.valueOf((char) 0x17), "<etb>");
            data = data.replaceAll(String.valueOf((char) 0x04), "<eot>");
            data = data.replaceAll(String.valueOf((char) 0x1E), "<RS>");

            String name = null;
            Double rate = null;

            Matcher nameMatcher = namePattern.matcher(data);
            Matcher rateMatcher = ratePattern.matcher(data);

            // name
            if (nameMatcher.find()) {
                name = nameMatcher.group(2);
            }


            // rate
            if (rateMatcher.find()) {
                String rateString = rateMatcher.group(2);
                try {
                    rate = Double.parseDouble(rateString);
                } catch (Exception e) {
                    rate = null;
                }
            }

            if (rate != null && name != null && rate != 0.0 && !"_NV".equals(name)) {
                // add to list
                InfusionData.Perfusion perfusion = new InfusionData.Perfusion(name, rate);
                perfs.add(perfusion);
            }

            // is this the end of the block?
            Matcher endMatcher = endPattern.matcher(data);
            if (endMatcher.find()) {
                String tag = endMatcher.group(1);
                if ("<etb>".equals(tag)) {
                    // there is at least one more block coming
                } else if ("<etx>".equals(tag)) {
                    // end of package

                    // save all infusions in separate result list
                    ArrayList<InfusionData.Perfusion> resultList = new ArrayList<>(perfs);

                    // cleanup
                    perfs.clear();

                    // return result
                    return new InfusionData(LocalDateTime.now(), resultList);
                }
            }
        } catch (Exception e) {
            throw new ConversionException(data, InfusionData.class);
        }
        return null;
    }
}
