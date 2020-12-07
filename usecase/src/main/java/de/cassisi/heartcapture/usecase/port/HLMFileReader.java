package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.HLMData;
import de.cassisi.heartcapture.usecase.exception.ReadHLMFileException;

import java.io.File;

public interface HLMFileReader {

    HLMData readHLMFile(File file) throws ReadHLMFileException;

}
