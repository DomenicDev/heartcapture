package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.ReportDocument;

public interface ReportDocumentExporter {

    void export(String path, ReportDocument result);

}
