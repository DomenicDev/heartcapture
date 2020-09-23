package de.cassisi.hearth.usecase;

import de.cassisi.hearth.entity.ReportDocument;
import de.cassisi.hearth.entity.ids.OperationId;
import de.cassisi.hearth.usecase.port.ReportDocumentExporter;
import de.cassisi.hearth.usecase.port.ReportRepository;

public class ExportReport {

    private ReportRepository reportRepository;
    private ReportDocumentExporter exporter;

    public ExportReport(ReportRepository reportRepository, ReportDocumentExporter exporter) {
        this.reportRepository = reportRepository;
        this.exporter = exporter;
    }

    public void export(OperationId operationId, String path) {
        ReportDocument reportDocument = reportRepository.findByOperation(operationId);
        exporter.export(path, reportDocument);
    }

}
