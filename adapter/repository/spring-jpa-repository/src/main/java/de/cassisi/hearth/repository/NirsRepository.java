package de.cassisi.hearth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NirsRepository extends JpaRepository<NirsRepository, Long> {
}
