package de.cassisi.heartcapture.usecase.output;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OutputHandler<OutputData> {

    void handle(OutputData outputData);

}
