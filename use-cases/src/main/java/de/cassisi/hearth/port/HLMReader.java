package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.HLMData;

public interface HLMReader {

    HLMData readFromFile(String path);

}
