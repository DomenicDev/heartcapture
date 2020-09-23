package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.HLMData;

public interface HLMReader {

    HLMData readFromFile(String path);

}
