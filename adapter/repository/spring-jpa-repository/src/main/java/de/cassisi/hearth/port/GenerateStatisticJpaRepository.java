package de.cassisi.hearth.port;

import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.dto.SimpleStatisticDTO;
import de.cassisi.hearth.usecase.port.GenerateStatisticRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenerateStatisticJpaRepository implements GenerateStatisticRepository {


    private final OperationRepository operationRepository;
    private final EntityManager entityManager;

    public GenerateStatisticJpaRepository(OperationRepository operationRepository, EntityManager entityManager) {
        this.operationRepository = operationRepository;
        this.entityManager = entityManager;
    }

    @Override
    public SimpleStatisticDTO generate() {

        SimpleStatisticDTO stats = new SimpleStatisticDTO();

        int numberOfOperations = (int) operationRepository.count();
        Long numberOfOpenOperations = entityManager.createQuery("SELECT count (op) FROM OperationDB op WHERE op.locked = false", Long.class).getSingleResult();
        Long numberOfIncompleteOperations = entityManager.createQuery("SELECT count(op) " +
                "FROM OperationDB op " +
                "WHERE op.anesthesiaDataAvailable = false " +
                "or op.infusionDataAvailable = false " +
                "or op.hlmDataAvailable = false " +
                "or op.nirsDataAvailable = false", Long.class).getSingleResult();

        int pastDays = 14;
        // OP DISTRIBUTION
        LocalDate start = LocalDate.now().minusDays(pastDays);
        LocalDate end = LocalDate.now();
        List<OperationDB> operationsLastTwoWeeks = operationRepository.findByDateBetweenOrderByDate(start, end);

        Map<LocalDate, Integer> opDerivation = new HashMap<>();
        for (int i = 0; i < pastDays; i++) {
            opDerivation.put(end.minusDays(i), 0);
        }
        for (OperationDB op : operationsLastTwoWeeks) {
            LocalDate date = op.getDate();
            opDerivation.putIfAbsent(date, 0);
            opDerivation.put(date, opDerivation.get(date) + 1);
        }

        // AGE DISTRIBUTION
        stats.ageDistribution.put("< 50", getAgeCountFor(0, 49));
        stats.ageDistribution.put("50 - 59", getAgeCountFor(50, 59));
        stats.ageDistribution.put("60 - 69", getAgeCountFor(60, 69));
        stats.ageDistribution.put("70 - 79", getAgeCountFor(70, 79));
        stats.ageDistribution.put(">= 80", getAgeCountFor(80, 200));

        // create result object

        stats.numberOfOperations = numberOfOperations;
        stats.numberOfOpenOperations = numberOfOpenOperations;
        stats.numberOfIncompleteOperations = numberOfIncompleteOperations;
        stats.numberOfOperationsLast2Weeks.putAll(opDerivation);

        return stats;
    }

    private long getAgeCountFor(int startAge, int endAge) {
        return entityManager.createQuery("SELECT count(hlm) from HLMDataDB  hlm WHERE hlm.patientDataDB.age between :start and :end", Long.class)
                .setParameter("start", startAge)
                .setParameter("end", endAge)
                .getSingleResult();
    }
}
