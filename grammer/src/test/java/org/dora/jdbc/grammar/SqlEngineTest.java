package org.dora.jdbc.grammar;

import java.sql.SQLException;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;

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
        String sql = "select * from table;";
        String parse = sqlEngine.parse(sql);
        Assert.assertTrue("convert to json and not null", Objects.nonNull(parse));
        JSONObject jsonObject = JSONObject.parseObject(parse);
        Assert.assertTrue("json format", Objects.nonNull(jsonObject));
    }

}
