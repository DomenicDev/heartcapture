package de.cassisi.heartcapture.port.repository;

import de.cassisi.heartcapture.repository.model.NirsDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NirsRepository extends JpaRepository<NirsDataDB, Long> {
}
