package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.HLMData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

class TestHLMExcelFileReader {

    @Test
    void testSameDay() throws URISyntaxException {
        HLMExcelFileReader reader = new HLMExcelFileReader();
        File excelFile = new File(Objects.requireNonNull(TestHLMExcelFileReader.class.getClassLoader().getResource("hlm_test_data_1.xlsx")).toURI());
        HLMData hlmData = reader.readHLMFile(excelFile);
        System.out.println(hlmData);
    }

    @Test
    void testMidnight() throws URISyntaxException {
        HLMExcelFileReader reader = new HLMExcelFileReader();
        File excelFile = new File(Objects.requireNonNull(TestHLMExcelFileReader.class.getClassLoader().getResource("hlm_test_data_2.xlsx")).toURI());
        reader.readHLMFile(excelFile);
    }

}
