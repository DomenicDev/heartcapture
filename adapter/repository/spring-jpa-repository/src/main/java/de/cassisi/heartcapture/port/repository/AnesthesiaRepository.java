package de.cassisi.heartcapture.port.repository;

import de.cassisi.heartcapture.repository.model.AnesthesiaDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnesthesiaRepository extends JpaRepository<AnesthesiaDataDB, Long> {
}
