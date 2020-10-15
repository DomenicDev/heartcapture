package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import java.time.LocalDate;

import static de.cassisi.hearth.usecase.CreateOperation.*;

public interface CreateOperation extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public LocalDate localDate;
        public String room;
    }

    class OutputData {
        public long id;
        public LocalDate date;
        public String room;
    }

}
