package de.cassisi.heartcapture.port.repository;

import de.cassisi.heartcapture.repository.model.InfusionDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfusionRepository extends JpaRepository<InfusionDataDB, Long> {
}
