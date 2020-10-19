package de.cassisi.hearth.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestInfusionData {

    @Test
    void testInfusionData() {
        LocalDateTime now = LocalDateTime.now();
        List<PerfusorData> perfusorData = getTestPerfusorData();

        InfusionData infusionData = new InfusionData(now, perfusorData);

        assertEquals(now, infusionData.getTimestamp());
        assertEquals(perfusorData, infusionData.getPerfusorDataList());
    }

    private List<PerfusorData> getTestPerfusorData() {
        List<PerfusorData> list = new ArrayList<>();
        list.add(new PerfusorData("a", 12));
        list.add(new PerfusorData("b", 2));
        return list;
    }

}
