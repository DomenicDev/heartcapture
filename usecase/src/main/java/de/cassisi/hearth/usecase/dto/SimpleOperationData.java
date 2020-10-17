package de.cassisi.hearth.usecase.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SimpleOperationData {

    private final long id;
    private final LocalDate date;
    private final String room;
    // more to come


    public SimpleOperationData(long id, LocalDate date, String room) {
        this.id = id;
        this.date = date;
        this.room = room;
    }


}
