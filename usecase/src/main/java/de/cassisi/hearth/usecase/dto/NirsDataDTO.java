package de.cassisi.hearth.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NirsDataDTO {

    private final LocalDateTime timestamp;
    private final int left;
    private final int right;

}
