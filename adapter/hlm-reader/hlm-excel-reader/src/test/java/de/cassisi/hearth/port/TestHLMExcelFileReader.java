package de.cassisi.hearth.port;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

class TestHLMExcelFileReader {

    @Test
    void testSameDay() {
        HLMExcelFileReader reader = new HLMExcelFileReader();
        File excelFile = new File(Objects.requireNonNull(TestHLMExcelFileReader.class.getClassLoader().getResource("hlm_test_data_1.xlsx")).getFile());
        reader.readHLMFile(excelFile);
    }

    @Test
    void testMidnight() {
        HLMExcelFileReader reader = new HLMExcelFileReader();
        File excelFile = new File(Objects.requireNonNull(TestHLMExcelFileReader.class.getClassLoader().getResource("hlm_test_data_2.xlsx")).getFile());
        reader.readHLMFile(excelFile);
    }

}
