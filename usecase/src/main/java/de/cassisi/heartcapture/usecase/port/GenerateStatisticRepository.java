package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.usecase.dto.SimpleStatisticDTO;

public interface GenerateStatisticRepository {

    SimpleStatisticDTO generate();

}
