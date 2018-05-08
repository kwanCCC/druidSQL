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
        String sql = "select num1, uid from table1 where timestamps between 1 and 2 and id <> 1";
        Query parse = sqlEngine.parse(sql);
        Assert.assertTrue("convert to json and not null", Objects.nonNull(parse));
    }

}
