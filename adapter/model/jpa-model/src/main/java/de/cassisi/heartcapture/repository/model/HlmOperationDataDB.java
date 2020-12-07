package de.cassisi.heartcapture.repository.model;

import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class HlmOperationDataDB {

    @ElementCollection
    private final List<String> previousOperations = new ArrayList<>();

    public void add(String operation) {
        this.previousOperations.add(operation);
    }

}
