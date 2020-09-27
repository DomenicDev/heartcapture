package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.InfusionDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfusionRepository extends JpaRepository<InfusionDataDB, Long> {
}
