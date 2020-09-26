package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.HLMData;

import java.io.File;

public interface HLMFileReader {

    HLMData readHLMFile(File file);

}
