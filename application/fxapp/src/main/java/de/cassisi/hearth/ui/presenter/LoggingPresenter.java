package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.config.Constants;
import de.cassisi.hearth.ui.event.RefreshLoggingDataEvent;
import de.cassisi.hearth.ui.view.logging.LoggingViewModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
public class LoggingPresenter extends FXPresenter<RefreshLoggingDataEvent> {

    private static final Logger LOGGER = Logger.getLogger(LoggingPresenter.class);

    private final LoggingViewModel viewModel;

    public LoggingPresenter(LoggingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(RefreshLoggingDataEvent data) {
        try {
            List<String> strings = Files.readAllLines(Paths.get(Constants.LOGGING_FILE));
            StringBuilder builder = new StringBuilder();
            strings.forEach(line -> builder.append(line).append(System.lineSeparator()));
            String logData = builder.toString();

            viewModel.getLoggingTextProperty().setValue(logData);

        } catch (IOException e) {
            LOGGER.error(e);
        }

    }
}
