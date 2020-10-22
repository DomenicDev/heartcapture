package de.cassisi.hearth.ui.presenter;

import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static de.cassisi.hearth.usecase.GenerateReport.OutputData;

@Component
public class GenerateReportPresenter extends FXPresenter<OutputData> {

    @Override
    public void runOnUI(OutputData outputData) {
        try {
            FileChooser fileChooser = new FileChooser();
            File fileToSave = fileChooser.showSaveDialog(null);

            if (fileToSave == null) {
                // user did not select a file, so return
                return;
            }

            // write to file
            FileOutputStream fos = new FileOutputStream(fileToSave);
            fos.write(outputData.reportFile);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
