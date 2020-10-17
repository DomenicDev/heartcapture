package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.port.FindAllOperationsRepository;
import de.cassisi.hearth.util.OperationConverter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FindAllOperationsJpaRepository implements FindAllOperationsRepository {

    private final EntityManager entityManager;

    public FindAllOperationsJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Operation> findOperations(int limit, boolean sortByLatest) {
        List<OperationDB> dbData = entityManager.createQuery("SELECT op FROM OperationDB op ORDER BY op.date DESC ", OperationDB.class)
                .setMaxResults(limit)
                .getResultList();

        List<Operation> result = new ArrayList<>();
        dbData.forEach(db -> result.add(OperationConverter.toOperation(db)));
        return result;
    }

}
