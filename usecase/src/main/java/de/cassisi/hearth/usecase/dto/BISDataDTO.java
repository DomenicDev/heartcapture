package de.cassisi.hearth.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BISDataDTO {

    private final LocalDateTime timestamp;
    private final double bsi;

}
