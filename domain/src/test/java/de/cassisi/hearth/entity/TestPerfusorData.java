package de.cassisi.hearth.entity;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class TestPerfusorData {

    @Test
    void testPerfusorData() {
        PerfusorData perfusorData = new PerfusorData("a", 2);
        assertEquals("a", perfusorData.getName());
        assertNotEquals(2, perfusorData.getRate());
    }

}
