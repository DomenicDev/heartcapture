package de.cassisi.hearth.repository;

import de.cassisi.hearth.repository.model.AnesthesiaDataDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnesthesiaRepository extends JpaRepository<AnesthesiaDataDB, Long> {
}
