package de.cassisi.hearth;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.repository.AnesthesiaRepository;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.AnesthesiaDataDB;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestAddAnesthesiaDataJpaRepository {

    private AnesthesiaRepository anesthesiaRepository = Mockito.mock(AnesthesiaRepository.class);
    private OperationRepository operationRepository = Mockito.mock(OperationRepository.class);

    private AddAnesthesiaDataJpaRepository jpaRepository = new AddAnesthesiaDataJpaRepository(anesthesiaRepository, operationRepository);

    @Test
    void testSuccessfullyAddingData() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.of(getTestOperationDB()));

        AnesthesiaData testAnesthesiaData = getTestAnesthesiaData();
        jpaRepository.addAnesthesiaData(1, testAnesthesiaData);

        verify(anesthesiaRepository).save(any(AnesthesiaDataDB.class));
    }

    @Test
    void testThrowOperationNotFoundException() {
        when(operationRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OperationNotFoundException.class, () ->
                jpaRepository.addAnesthesiaData(1L, getTestAnesthesiaData()));
    }

    private AnesthesiaData getTestAnesthesiaData() {
        return new AnesthesiaData(LocalDateTime.now(), 32);
    }

    private OperationDB getTestOperationDB() {
        return OperationDB.builder().build();
    }
}
