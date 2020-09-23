package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.OperationDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationJpaRepository extends JpaRepository<OperationDB, Long> {
}
