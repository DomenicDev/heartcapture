package de.cassisi.hearth;

import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.PerfusorData;
import de.cassisi.hearth.repository.model.InfusionDataDB;
import de.cassisi.hearth.repository.model.OperationDB;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = Config.class)
@RunWith(SpringRunner.class)
@DataJpaTest
class TestAddInfusionJpaRepository {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddInfusionDataJpaRepository infusionRepository;


    @Test
    void testSuccessfulAddingInfusionData() {
        OperationDB operationDB = new OperationDB();
        operationDB.setId(null);
        operationDB.setDate(LocalDate.now());
        operationDB.setRoomNr("Room 4");

        operationDB = entityManager.persist(operationDB);

        InfusionData infusionDataDB = new InfusionData(LocalDateTime.now(), Arrays.asList(new PerfusorData("medi", 3)));

        infusionRepository.addInfusionDataToOperation(1L, infusionDataDB);

        InfusionDataDB saved = entityManager.find(InfusionDataDB.class, 1L);
        assertNotNull(saved);
    }

}
