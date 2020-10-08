package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.*;

import java.util.List;

public interface ReportFileGenerator {

    byte[] generateReport(Operation operation, HLMData hlmData, List<InfusionData> infusionData, List<AnesthesiaData> anesthesiaData, List<NIRSData> nirsData);

}
