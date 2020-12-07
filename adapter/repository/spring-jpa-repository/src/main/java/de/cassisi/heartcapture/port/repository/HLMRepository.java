package de.cassisi.heartcapture.port.repository;

import de.cassisi.heartcapture.repository.model.HLMDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HLMRepository extends JpaRepository<HLMDataDB, Long> {
}
