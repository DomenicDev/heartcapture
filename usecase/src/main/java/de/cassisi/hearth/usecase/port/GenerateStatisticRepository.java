package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.usecase.dto.SimpleStatisticDTO;

public interface GenerateStatisticRepository {

    SimpleStatisticDTO generate();

}
