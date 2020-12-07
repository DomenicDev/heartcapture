package de.cassisi.heartcapture.port.repository;

import de.cassisi.heartcapture.repository.model.PerfusorDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfusorRepository extends JpaRepository<PerfusorDataDB, Long> {
}
