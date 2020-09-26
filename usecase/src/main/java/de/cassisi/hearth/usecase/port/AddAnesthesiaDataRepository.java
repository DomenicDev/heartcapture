package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.AnesthesiaData;

public interface AddAnesthesiaDataRepository {

    void addAnesthesiaData(long operationId, AnesthesiaData anesthesiaData);

}
