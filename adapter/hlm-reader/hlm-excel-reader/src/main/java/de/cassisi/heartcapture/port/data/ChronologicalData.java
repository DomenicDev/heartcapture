package de.cassisi.heartcapture.port.data;

import java.time.LocalDateTime;

/**
 * Implies that classes which implement this interface have a chronological dependency.
 * The data represent the state at a certain time.
 */
public interface ChronologicalData {

    void setTimestamp(LocalDateTime timestamp);

    LocalDateTime getTimestamp();

}
