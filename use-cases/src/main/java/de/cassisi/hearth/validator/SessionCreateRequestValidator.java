package de.cassisi.hearth.validator;

import de.cassisi.hearth.dto.SessionCreateRequest;
import de.cassisi.hearth.exception.SessionCreateRequestValidationException;

public class SessionCreateRequestValidator {

    public static void validate(final SessionCreateRequest request) {
        if (request == null) throw new SessionCreateRequestValidationException("request must not be null");
        if (request.getDate() == null || request.getRoom() == null) throw new SessionCreateRequestValidationException("Date and request must have valid values");
    }

}
