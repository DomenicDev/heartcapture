package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.usecase.output.OutputHandler;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static de.cassisi.hearth.usecase.GenerateReport.OutputData;

@Component
public class GenerateReportPresenter implements OutputHandler<OutputData> {


    @Override
    public void handle(OutputData outputData) {
        try {

            FileChooser fileChooser = new FileChooser();
            File fileToSave = fileChooser.showSaveDialog(null);

            FileOutputStream fos = new FileOutputStream(fileToSave);
            fos.write(outputData.reportFile);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
