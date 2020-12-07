package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.Operation;

import java.util.List;

public interface FindAllOperationsRepository {

    List<Operation> findOperations(int limit, boolean sortByLatest);

}
