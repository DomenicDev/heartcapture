package de.cassisi.hearth.ui.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LatestOperationTableData {

    private final long id;
    private final String date;
    private final String room;

}
