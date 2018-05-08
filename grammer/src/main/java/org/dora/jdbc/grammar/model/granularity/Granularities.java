package org.dora.jdbc.grammar.model.granularity;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Created by SDE on 2018/5/8.
 */
public class Granularities {

    public static final Granularity ALL =  new SimpleGranularity("ALL");
    public static final Granularity DAY = new DurationGranularity(1000 * 60 * 60 * 24, 0);
    public static final Granularity HOUR = new DurationGranularity(1000 * 60 * 60, 0);
    public static final Granularity THIRTY_MINUTE = new DurationGranularity(1000 * 60 * 30, 0);
    public static final Granularity FIFTEEN_MINUTE = new DurationGranularity(1000 * 60 * 15, 0);
    public static final Granularity MINUTE = new DurationGranularity(1000 * 60, 0);

    public static final Map<String, Granularity> LiteralDurationMap = ImmutableMap.of(
        "DAY", DAY,
        "HOUR", HOUR,
        "THIRTY_MINUTE", THIRTY_MINUTE,
        "FIFTEEN_MINUTE", FIFTEEN_MINUTE,
        "MINUTE", MINUTE
    );

    private Granularities() {}

    public static Granularity fromPeriod(String period, String timeZone, long origin) {
        return new PeriodGranularity(period, timeZone, origin);
    }

    public static Granularity fromDuration(long duration, long origin) {
        return new DurationGranularity(duration, origin);
    }
}
