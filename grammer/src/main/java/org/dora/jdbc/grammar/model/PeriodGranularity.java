package org.dora.jdbc.grammar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.time.Period;

/**
 * http://druid.io/docs/0.8.0/querying/granularities.html
 *
 * @author zhxiaog
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PeriodGranularity implements Granularity
{
    private final String type = "period";
    private final String period;
    private final String timeZone;
    private final long   origin;
    
    @Override
    public long getValue() {
        return Period.parse(period).getMillis();
    }
}
