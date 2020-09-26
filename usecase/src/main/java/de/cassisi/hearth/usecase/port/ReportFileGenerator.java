package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;

import java.io.File;
import java.util.List;

public interface ReportFileGenerator {

    File generateReport(HLMData hlmData, List<InfusionData> infusionData, List<AnesthesiaData> anesthesiaData, List<NIRSData> nirsData);

}
