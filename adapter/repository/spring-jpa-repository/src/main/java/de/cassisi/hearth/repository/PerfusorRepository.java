package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.PerfusorDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfusorRepository extends JpaRepository<PerfusorDataDB, Long> {
}
