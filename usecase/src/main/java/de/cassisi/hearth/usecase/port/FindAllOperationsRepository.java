package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.Operation;

import java.util.List;

public interface FindAllOperationsRepository {

    List<Operation> findOperations(int limit, boolean sortByLatest);

}
