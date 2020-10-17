package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import java.util.ArrayList;
import java.util.List;

import static de.cassisi.hearth.usecase.FindAllOperations.*;

public interface FindAllOperations extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public int limit;
        public boolean sortByLatest;
    }

    class OutputData {
        public final List<SimpleOperationData> simpleOperationDataList = new ArrayList<>();
    }

}
