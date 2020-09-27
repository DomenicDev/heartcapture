package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.repository.AnesthesiaRepository;
import de.cassisi.hearth.repository.model.AnesthesiaDataDB;
import de.cassisi.hearth.usecase.port.AddAnesthesiaDataRepository;
import org.springframework.stereotype.Component;

@Component
public class AddAnesthesiaDataJpaRepository implements AddAnesthesiaDataRepository {

    private AnesthesiaRepository anesthesiaRepository;

    public AddAnesthesiaDataJpaRepository(AnesthesiaRepository anesthesiaRepository) {
        this.anesthesiaRepository = anesthesiaRepository;
    }

    @Override
    public void addAnesthesiaData(long operationId, AnesthesiaData anesthesiaData) {
        AnesthesiaDataDB anesthesiaDataDB = new AnesthesiaDataDB();
        // todo build

        anesthesiaRepository.save(anesthesiaDataDB);
    }
}
