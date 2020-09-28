package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.NirsDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NirsRepository extends JpaRepository<NirsDataDB, Long> {
}
