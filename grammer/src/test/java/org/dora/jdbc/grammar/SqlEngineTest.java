package org.dora.jdbc.grammar;

import java.sql.SQLException;
import java.util.Objects;

import org.dora.jdbc.grammar.parse.Query;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by SDE on 2018/5/7.
 */
public class SqlEngineTest {

    static SqlEngine sqlEngine;

    @BeforeClass
    public static void setup() {
        sqlEngine = SqlEngine.SQL_ENGINE;
    }

    @Test
    public void sqlEngine_could_convert_sql_to_druid_json() throws SQLException {
        String sql_a = "select num1, uid from table1 where timestamps between 1 and 2 and id <> 1";
        String sql_b
            = "select longSum(uid) from table1 where timestamps between 1 and 2 group by uid order by uid limit 5 "
            + "slice by 1000";
        String sql_c = "select num1 as num, uid from table1 where timestamps between 1 and 2 and id <> 1";
        Query parse_a = sqlEngine.parse(sql_a);
        Query parse_b = sqlEngine.parse(sql_b);
        Query parse_c = sqlEngine.parse(sql_c);
        Assert.assertTrue("convert to json and not null", Objects.nonNull(parse_a));
        Assert.assertTrue("convert to json and not null", Objects.nonNull(parse_b));
        Assert.assertTrue("convert to json and not null", Objects.nonNull(parse_c));
    }

}
