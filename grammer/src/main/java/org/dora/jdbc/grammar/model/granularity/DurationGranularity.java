package org.dora.jdbc.grammar.model.granularity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dora.jdbc.grammar.model.granularity.Granularity;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class DurationGranularity implements Granularity
{

    private final String type = "duration";
    /**
     * milliseconds
     */
    private final long duration;
    private final long origin;
    
    @Override
    public long getValue() {
        return duration;
    }
}
