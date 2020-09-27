package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.HLMDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HLMRepository extends JpaRepository<HLMDataDB, Long> {
}
