package de.cassisi.hearth.util;

import de.cassisi.hearth.entity.HLMEventData;
import de.cassisi.hearth.repository.model.HlmEventDataDB;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

class TestDBConverter {

    @Test
    void testEventData() {
        // create test data
        LocalDateTime now = LocalDateTime.now();
        HLMEventData.Unit unit = HLMEventData.Unit.ML;
        HLMEventData.EventType eventType = HLMEventData.EventType.ACT;
        Integer amount = 2;
        String free = "Y";
        HLMEventData.Typ typ = HLMEventData.Typ.COM;

        // create entity object
        HLMEventData eventData = HLMEventData.builder()
                .timestamp(now)
                .typ(typ)
                .type(eventType)
                .amount(amount)
                .unit(unit)
                .free(free)
                .build();

        // convert to database object
        HlmEventDataDB db = DBConverter.convert(eventData);

        // validate
        assertEquals(now, db.getTimestamp());
        assertEquals(typ, db.getTyp());
        assertEquals(eventType, db.getEventType());
        assertEquals(amount, db.getAmount());
        assertEquals(free, db.getFree());
        assertEquals(unit, db.getUnit());
    }

}
