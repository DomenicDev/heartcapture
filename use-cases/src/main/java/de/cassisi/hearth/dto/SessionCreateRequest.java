package de.cassisi.hearth.dto;

import java.time.LocalDate;

public final class SessionCreateRequest {

    private final LocalDate date;
    private final String room;

    public SessionCreateRequest(LocalDate date, String room) {
        this.date = date;
        this.room = room;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRoom() {
        return room;
    }
}
