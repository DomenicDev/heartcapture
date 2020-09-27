package de.cassisi.hearth.usecase.dto;

import java.time.LocalDate;


public class SimpleOperationData {

    public long id;
    public LocalDate localDate;
    public String rommBr;
    // more to come


    public SimpleOperationData(long id, LocalDate localDate, String rommBr) {
        this.id = id;
        this.localDate = localDate;
        this.rommBr = rommBr;
    }
}
