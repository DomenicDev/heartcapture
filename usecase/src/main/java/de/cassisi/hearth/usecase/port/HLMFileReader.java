package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.usecase.exception.ReadHLMFileException;

import java.io.File;

public interface HLMFileReader {

    HLMData readHLMFile(File file) throws ReadHLMFileException;

}
