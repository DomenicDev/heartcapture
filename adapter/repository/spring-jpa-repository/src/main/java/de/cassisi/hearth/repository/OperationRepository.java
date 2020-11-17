package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.OperationDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<OperationDB, Long> {

    List<OperationDB> findByDateBetweenOrderByDate(LocalDate from, LocalDate to);

}
