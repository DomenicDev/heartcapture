package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.GenerateStatistic;
import de.cassisi.heartcapture.usecase.dto.SimpleStatisticDTO;
import de.cassisi.heartcapture.usecase.port.GenerateStatisticRepository;

public class GenerateStatisticInteractor implements GenerateStatistic {

    private final GenerateStatisticRepository repository;

    public GenerateStatisticInteractor(GenerateStatisticRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Void input, OutputHandler<SimpleStatisticDTO> outputHandler) {
        SimpleStatisticDTO stats = repository.generate();
        outputHandler.handle(stats);
    }
}
