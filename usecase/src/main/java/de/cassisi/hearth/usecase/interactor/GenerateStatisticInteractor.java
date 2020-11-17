package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.usecase.GenerateStatistic;
import de.cassisi.hearth.usecase.dto.SimpleStatisticDTO;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.GenerateStatisticRepository;

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
