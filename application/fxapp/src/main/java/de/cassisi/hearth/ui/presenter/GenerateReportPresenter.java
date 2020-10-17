package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import static de.cassisi.hearth.usecase.GenerateReport.OutputData;

@Component
public class GenerateReportPresenter implements OutputHandler<OutputData> {


    @Override
    public void handle(OutputData outputData) {
        try {
            FileOutputStream fos = new FileOutputStream("build/testexport.xlsx");
            fos.write(outputData.reportFile);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
