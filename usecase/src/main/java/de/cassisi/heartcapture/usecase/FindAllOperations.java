package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.dto.SimpleOperationData;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;

import java.util.ArrayList;
import java.util.List;

import static de.cassisi.heartcapture.usecase.FindAllOperations.*;

public interface FindAllOperations extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public int limit;
        public boolean sortByLatest;
    }

    class OutputData {
        public final List<SimpleOperationData> simpleOperationDataList = new ArrayList<>();
    }

}
