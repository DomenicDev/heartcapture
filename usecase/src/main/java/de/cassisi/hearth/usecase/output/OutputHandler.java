package de.cassisi.hearth.usecase.output;

public interface OutputHandler<OutputData> {

    void handle(OutputData outputData);

}
